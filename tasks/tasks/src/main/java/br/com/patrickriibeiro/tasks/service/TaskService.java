package br.com.patrickriibeiro.tasks.service;

import br.com.patrickriibeiro.tasks.repository.TaskCustomRepository;
import br.com.patrickriibeiro.tasks.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.patrickriibeiro.tasks.model.Task;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TaskService {

    private final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    private final TaskCustomRepository taskCustomRepository;

    public TaskService(TaskRepository taskRepository, TaskCustomRepository taskCustomRepository) {
        this.taskRepository = taskRepository;
        this.taskCustomRepository = taskCustomRepository;
    }

    public Mono<Task> insert(Task task) {
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save)
                .doOnError(error -> LOGGER.info("Error during save task. Title: {}", task.getTitle(), error));
                //.onErrorResume(it -> Mono.just(Task.builder().withTitle("Error").build())); // Retorna um obj default.
    };

    public Mono<List<Task>> list(){
        return Mono.just(taskRepository.findAll());
    }

    public Page<Task> findPaginated(Task task, int pageNumber, Integer pageSize) {
            return taskCustomRepository.findPaginated(task,pageNumber,pageSize);
    }

    public Mono<Void> deleteById(String id){
        return Mono.fromRunnable( () -> taskRepository.deleteById(id));
    }

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .doOnNext(t -> LOGGER.info("Saving task with title {}", t.getTitle()))
                .map(taskRepository::save);
    };

}
