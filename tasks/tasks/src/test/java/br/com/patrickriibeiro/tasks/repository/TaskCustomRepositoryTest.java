package br.com.patrickriibeiro.tasks.repository;

import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class TaskCustomRepositoryTest {

    @InjectMocks
    private TaskCustomRepository taskCustomRepository;

    @Mock
    private ReactiveMongoOperations mongoOperations;

    @Test
    void customRepository_mustReturnPageWithOneElement_whenSendTask(){
        Task task = TestUtils.buildValidTestTask();
        Mockito.when(mongoOperations.find(any(),any())).thenReturn(Flux.just(task));
        Mockito.when(mongoOperations.count(any(Query.class), ArgumentMatchers.eq(Task.class))).thenReturn(Mono.just(1L));
        Mono<Page<Task>> result = taskCustomRepository.findPaginated(task,0,10);
        assertNotNull(result);
        assertEquals(1, Objects.requireNonNull(result.block()).getNumberOfElements());
    }
}
