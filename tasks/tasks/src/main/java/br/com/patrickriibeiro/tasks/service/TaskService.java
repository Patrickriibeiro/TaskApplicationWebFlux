package br.com.patrickriibeiro.tasks.service;

import br.com.patrickriibeiro.tasks.controller.dto.TaskDTO;
import org.springframework.stereotype.Service;

import br.com.patrickriibeiro.tasks.model.Task;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    public static List<Task> taskList = new ArrayList<Task>();

    public Mono<Task> insert(Task task) {
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(
                this::save
        );
    };

    public Mono<List<Task>> list(){
        return Mono.just(taskList);
    }

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .map(Task::newTask);
    };
}
