package br.com.patrickriibeiro.tasks.service;

import br.com.patrickriibeiro.tasks.controller.dto.TaskDTO;
import br.com.patrickriibeiro.tasks.controller.repository.TaskCustomRepository;
import br.com.patrickriibeiro.tasks.controller.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.patrickriibeiro.tasks.model.Task;
import reactor.core.publisher.Mono;

import javax.print.DocFlavor;
import java.util.ArrayList;
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

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .map(taskRepository::save);
    };

    public Page<Task> findPaginated(Task task, int pageNumber, Integer pageSize) {
            return taskCustomRepository.findPaginated(task,pageNumber,pageSize);
    }

    public Mono<Void> deleteById(String id){
        return Mono.fromRunnable( () -> taskRepository.deleteById(id));
    }

}
