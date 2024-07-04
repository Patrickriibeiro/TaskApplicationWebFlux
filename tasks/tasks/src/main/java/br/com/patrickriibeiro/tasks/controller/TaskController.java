package br.com.patrickriibeiro.tasks.controller;

import br.com.patrickriibeiro.tasks.controller.converter.TaskDTOConverter;
import br.com.patrickriibeiro.tasks.controller.dto.TaskDTO;
import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.service.TaskService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    private final TaskDTOConverter converter;

    public TaskController(TaskService taskService, TaskDTOConverter converter) {
        this.taskService = taskService;
        this.converter = converter;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<TaskDTO>> getTasks(){
        return taskService.list()
                .map(converter::convertList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TaskDTO> createTask(@RequestBody Task task){
        return taskService.insert(task)
                .map(converter::convert);
    }

}
