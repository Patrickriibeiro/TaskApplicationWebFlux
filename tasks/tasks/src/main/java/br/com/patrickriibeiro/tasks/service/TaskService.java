package br.com.patrickriibeiro.tasks.service;

import br.com.patrickriibeiro.tasks.repository.TaskCustomRepository;
import br.com.patrickriibeiro.tasks.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.patrickriibeiro.tasks.model.Task;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskCustomRepository taskCustomRepository;

    public TaskService(TaskRepository taskRepository, TaskCustomRepository taskCustomRepository) {
        this.taskRepository = taskRepository;
        this.taskCustomRepository = taskCustomRepository;
    }

    public Mono<Task> insert(Task task) {
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save);
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
                .map(taskRepository::save);
    };

}
