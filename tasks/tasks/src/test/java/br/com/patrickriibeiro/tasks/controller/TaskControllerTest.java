package br.com.patrickriibeiro.tasks.controller;

import br.com.patrickriibeiro.tasks.controller.converter.TaskDTOConverter;
import br.com.patrickriibeiro.tasks.controller.dto.TaskDTO;
import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest
public class TaskControllerTest {

    @InjectMocks
    private TaskController controller;

    @Mock
    private TaskService service;

    @Mock
    private TaskDTOConverter converter;

    @Test
    public void controller_mustReturnOk_whenSaveSuccessFully(){
        Mockito.when(service.insert(Mockito.any())).thenReturn(Mono.just(new Task()));
        Mockito.when(converter.convert(Mockito.any(Task.class))).thenReturn(new TaskDTO());
        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.post()
                .uri("/task")
                .bodyValue(new Task())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class);
        ;
    }


}
