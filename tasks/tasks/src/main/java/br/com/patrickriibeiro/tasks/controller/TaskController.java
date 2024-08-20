package br.com.patrickriibeiro.tasks.controller;

import br.com.patrickriibeiro.tasks.controller.converter.TaskDTOConverter;
import br.com.patrickriibeiro.tasks.controller.converter.TaskInsertDTOConverter;
import br.com.patrickriibeiro.tasks.controller.dto.TaskDTO;
import br.com.patrickriibeiro.tasks.controller.dto.TaskInsertDTO;
import br.com.patrickriibeiro.tasks.model.TaskState;
import br.com.patrickriibeiro.tasks.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    private final TaskDTOConverter converter;

    private final TaskInsertDTOConverter insertDTOConverter;

    public TaskController(TaskService taskService, TaskDTOConverter converter, TaskInsertDTOConverter insertDTOConverter) {
        this.taskService = taskService;
        this.converter = converter;
        this.insertDTOConverter = insertDTOConverter;
    }

    @GetMapping("/paginated")
    public Mono<Page<TaskDTO>> getTasks(@RequestParam(required = false) String id,
                                  @RequestParam(required = false) String title,
                                  @RequestParam(required = false) String description,
                                  @RequestParam(required = false, defaultValue = "0") int priority,
                                  @RequestParam(required = false) TaskState taskState,
                                  @RequestParam(value = "pagerNumber", defaultValue = "0") int pageNumber,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return taskService.findPaginated(converter.convert(id, title, description, priority, taskState),pageNumber,pageSize)
                .map( it -> it.map(converter::convert));
    }

    @PostMapping
    public Mono<TaskDTO> createTask(@RequestBody TaskInsertDTO taskInsertDTO){
        return taskService.insert(insertDTOConverter.convert(taskInsertDTO))
                .doOnNext(task -> LOGGER.info("Saved task with id {}", task.getId()))
                .map(converter::convert);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id){
        return Mono.just(id)
                .doOnNext(it -> LOGGER.info("Deleting task with id {}", it))
                .flatMap(taskService::deleteById);
    }


}
