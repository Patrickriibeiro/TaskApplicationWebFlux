package br.com.patrickriibeiro.tasks.repository;

import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.test.context.junit4.statements.SpringRepeat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class TaskCustomRepositoryTest {

    @InjectMocks
    private TaskCustomRepository taskCustomRepository;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    void customRepository_mustReturnPageWithOneElement_whenSendTask(){
        Task task = TestUtils.buildValidTestTask();
        Mockito.when(mongoOperations.find(any(),any())).thenReturn(List.of(task));
        Page<Task> result = taskCustomRepository.findPaginated(task,0,10);
        assertNotNull(result);
        assertEquals(1,result.getNumberOfElements());
    }
}
