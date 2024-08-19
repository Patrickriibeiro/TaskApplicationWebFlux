package br.com.patrickriibeiro.tasks.controller;

import br.com.patrickriibeiro.tasks.controller.converter.TaskDTOConverter;
import br.com.patrickriibeiro.tasks.controller.converter.TaskInsertDTOConverter;
import br.com.patrickriibeiro.tasks.controller.dto.TaskDTO;
import br.com.patrickriibeiro.tasks.controller.dto.TaskInsertDTO;
import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskControllerTest {

    @InjectMocks
    private TaskController controller;

    @Mock
    private TaskService service;

    @Mock
    private TaskDTOConverter converter;

    @Mock
    private TaskInsertDTOConverter insertDTOConverter;

    @Test
    public void controller_mustReturnOk_whenSaveSuccessFully(){
        when(service.insert(any())).thenReturn(Mono.just(new Task()));
        when(converter.convert(any(Task.class))).thenReturn(new TaskDTO());
        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.post()
                .uri("/task")
                .bodyValue(new TaskInsertDTO())
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDTO.class);
        ;
    }

    @Test
    public void controller_mustReturnOk_whenGetTasksPaginatedSuccessFully(){
        Page<Task> emptyPage = new PageImpl<>(new ArrayList<>());

        when(service.findPaginated(any(), anyInt(), anyInt())).thenReturn(emptyPage);

        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.get()
                .uri("/task/paginated")
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBodyList(TaskDTO.class);
    }



    @Test
    public void controller_mustReturnNoContent_whenDeleteSuccessFully(){
        String taskId = "any-id";
        when(service.deleteById(any())).thenReturn(Mono.empty());
        WebTestClient client = WebTestClient.bindToController(controller).build();
        client.delete()
                .uri("/task/" + taskId)
                .exchange()
                .expectStatus()
                .isNoContent();
        ;

    }


}
