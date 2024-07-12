package br.com.patrickriibeiro.tasks.utils;

import br.com.patrickriibeiro.tasks.controller.dto.TaskDTO;
import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.model.TaskState;

public class TestUtils {

    public static Task buildValidTestTask(){
        return Task.builder()
                .withId("123")
                .withTitle("title")
                .withDescription("bu")
                .withPriority(1)
                .withState(TaskState.INSERT)
                .build();
    }

    public static TaskDTO buildValidTestTaskDTO(){
        TaskDTO dto = new TaskDTO();
        dto.setId("123");
        dto.setTitle("title");
        dto.setDescription("bu");
        dto.setPriority(1);
        dto.setState(TaskState.INSERT);
        return dto;
    }
}
