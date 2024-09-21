package br.com.patrickriibeiro.tasks.service;

import br.com.patrickriibeiro.tasks.exception.TaskNotFoundException;
import br.com.patrickriibeiro.tasks.messaging.TaskNotificationProducer;
import br.com.patrickriibeiro.tasks.model.Address;
import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.repository.TaskCustomRepository;
import br.com.patrickriibeiro.tasks.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    private final TaskCustomRepository taskCustomRepository;

    private final AddressService addressService;

    private final TaskNotificationProducer taskNotificationProducer;

    public TaskService(TaskRepository taskRepository, TaskCustomRepository taskCustomRepository,AddressService addressService, TaskNotificationProducer taskNotificationProducer) {
        this.taskRepository = taskRepository;
        this.taskCustomRepository = taskCustomRepository;
        this.addressService = addressService;
        this.taskNotificationProducer = taskNotificationProducer;
    }

    public Mono<Task> insert(Task task) {
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save)
                .doOnError(error -> LOGGER.info("Error during save task. Title: {}", task.getTitle(), error));
                //.onErrorResume(it -> Mono.just(Task.builder().withTitle("Error").build())); // Retorna um obj default.
    }
    
    public Mono<Page<Task>> findPaginated(Task task, int pageNumber, Integer pageSize) {
            return taskCustomRepository.findPaginated(task,pageNumber,pageSize);
    }

    public Mono<Void> deleteById(String id){
        return taskRepository.deleteById(id);
    }

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .doOnNext(t -> LOGGER.info("Saving task with title {}", t.getTitle()))
                .flatMap(taskRepository::save);
    }

    public Mono<Task> update(Task task) {
        return taskRepository.findById(task.getId())
                .map(task::update)
                .flatMap(taskRepository::save)
                .switchIfEmpty(Mono.error(TaskNotFoundException::new))
                .doOnError(error -> LOGGER.error("Error during update task with id: {}, Message: {}", task.getId(), error.getMessage()));
    }

    public Mono<Task> start(String id, String zipCode){
        return taskRepository.findById(id)
                .zipWhen( it -> addressService.getAddress(zipCode))
                .flatMap( it -> updateAddress(it.getT1(),it.getT2()))
                .map(Task::start)
                .flatMap(taskRepository::save)
                .flatMap(taskNotificationProducer::sendNotification)
                .switchIfEmpty(Mono.error(TaskNotFoundException::new))
                .doOnError(error -> LOGGER.error("Error on start task. ID: {}", id, error))
                .retryWhen(Retry
                        .backoff(3, Duration.ofSeconds(1))
                        .maxBackoff(Duration.ofSeconds(10))
                        .jitter(0.5)
                        .filter(throwable -> throwable instanceof TaskNotFoundException));
    }

    public Mono<Task> done(Task task) {
        return Mono.just(task)
                .doOnNext(it -> LOGGER.info("Finishing task. ID: {}", task.getId()))
                .map(Task::done)
                .flatMap(taskRepository::save);
    }

    public Mono<List<Task>> doneMany(List<String> ids) {
        return Flux.fromIterable(ids)
                .flatMap(id -> taskRepository.findById(id)
                        .map(Task::done)
                        .flatMap(taskRepository::save)
                        .doOnNext(it -> LOGGER.info("Done task. ID: {}", it.getId())))
                .collectList();
    }

    private Mono<Task> updateAddress(Task task, Address address){
        return Mono.just(task)
                .map(it -> task.updateAddress(address));
    }

    public Flux<Task> refreshCreated() {
        return taskRepository.findAll()
                .filter(Task::createdIsEmpty)
                .map(Task::createdNow)
                .flatMap(taskRepository::save);
    }

    @PostConstruct
    private void scheduleDoneOlderTasks(){
        Mono.delay(Duration.ofSeconds(5))
                .doOnNext(it -> LOGGER.info("Starting task Monitoring"))
                .subscribe();

        Flux.interval(Duration.ofSeconds(10))
                .flatMap(it -> doneOlderTasks())
                .filter(tasks -> tasks > 0)
                .doOnNext(tasks -> LOGGER.info("{} tasks completed after being active for over 7 days.", tasks))
                .subscribe();

    }

    private Mono<Long> doneOlderTasks() {
        return taskCustomRepository.updateStateToDoneForOlderTasks(LocalDate.now().minusDays(7));
    }

}
