package br.com.patrickriibeiro.tasks.controller.converter;

import br.com.patrickriibeiro.tasks.controller.dto.TaskInsertDTO;
import br.com.patrickriibeiro.tasks.model.Task;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskInsertDTOConverter {

    public Task convert(TaskInsertDTO dto) {
        return Optional.ofNullable(dto).map( source ->
                Task.builder()
                        .withTitle(source.getTitle())
                        .withDescription(source.getDescription())
                        .withPriority(source.getPriority())
                        .build()).orElse(null);
    }
}
