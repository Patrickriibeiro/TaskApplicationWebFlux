package br.com.patrickriibeiro.tasks.controller.conveter;

import br.com.patrickriibeiro.tasks.controller.converter.TaskDTOConverter;
import br.com.patrickriibeiro.tasks.controller.dto.TaskDTO;
import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.model.TaskState;
import br.com.patrickriibeiro.tasks.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskDTOConverterTest {

    @InjectMocks
    private TaskDTOConverter converter;

    @Test
    void converter_mustReturnTaskDTO_whenInputTask(){
        Task task = TestUtils.buildValidTestTask();
        TaskDTO dto = converter.convert(task);

        Assertions.assertEquals(dto.getId(), task.getId());
        Assertions.assertEquals(dto.getTitle(), task.getTitle());
        Assertions.assertEquals(dto.getDescription(), task.getDescription());
        Assertions.assertEquals(dto.getPriority(), task.getPriority());
        Assertions.assertEquals(dto.getState(), task.getState());

    }

    @Test
    void converter_mustReturnTask_whenInputTaskDTO(){
        TaskDTO dto = TestUtils.buildValidTestTaskDTO();
        Task task = converter.convert(dto);

        Assertions.assertEquals(task.getId(),dto.getId());
        Assertions.assertEquals(task.getTitle(),dto.getTitle());
        Assertions.assertEquals(task.getDescription(),dto.getDescription());
        Assertions.assertEquals(task.getPriority(),dto.getPriority());
        Assertions.assertEquals(task.getState(),dto.getState());

    }

    @Test
    void converter_mustReturnTask_whenInputParameters(){
        Task dto = converter.convert("123","title","bu",1,TaskState.INSERT);

        Assertions.assertEquals(dto.getId(), "123");
        Assertions.assertEquals(dto.getTitle(), "title");
        Assertions.assertEquals(dto.getDescription(), "bu");
        Assertions.assertEquals(dto.getPriority(), 1);
        Assertions.assertEquals(dto.getState(), TaskState.INSERT);

    }



}
