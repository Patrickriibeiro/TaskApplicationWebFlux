package br.com.patrickriibeiro.tasks.service;

import br.com.patrickriibeiro.tasks.controller.dto.TaskDTO;
import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.repository.TaskCustomRepository;
import br.com.patrickriibeiro.tasks.repository.TaskRepository;
import br.com.patrickriibeiro.tasks.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTest {

    @InjectMocks
    private TaskService service;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskCustomRepository taskCustomRepository;

    @Test
    void service_mustReturnTask_whenInsertSuccessFully(){
        Task task = TestUtils.buildValidTestTask();

        when(taskRepository.save(any())).thenReturn(task);

        StepVerifier.create(service.insert(task))
                .then( () -> Mockito.verify(taskRepository, times(1)).save(any()))
                .expectNext(task)
                .expectComplete();
    }

    @Test
    void service_mustReturnVoid_whenDeleteTaskSuccessFully(){
        StepVerifier.create(service.deleteById("someId"))
                .then(() -> verify(taskRepository,times(1)).deleteById(any()))
                .verifyComplete();
    }

    @Test
    void service_mustReturnTaskPage_whenFindPaginated(){
        Task task = TestUtils.buildValidTestTask();

        when(taskCustomRepository.findPaginated(any(),anyInt(),anyInt())).thenReturn(Page.empty());
        Page<Task> result = service.findPaginated(task,0,10);

        assertNotNull(result);
        verify(taskCustomRepository, times(1)).findPaginated(any(),anyInt(),anyInt());
    }

}
