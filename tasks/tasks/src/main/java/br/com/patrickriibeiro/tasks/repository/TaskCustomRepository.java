package br.com.patrickriibeiro.tasks.repository;

import br.com.patrickriibeiro.tasks.model.Task;
import br.com.patrickriibeiro.tasks.model.TaskState;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TaskCustomRepository {

    private final ReactiveMongoOperations mongoOperations;

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public TaskCustomRepository(ReactiveMongoOperations mongoOperations, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.mongoOperations = mongoOperations;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<Page<Task>> findPaginated(Task task, Integer page, Integer size){
            return queryExample(task)
                    .zipWith(pageable(page,size))
                    .flatMap(it -> execute(task, it.getT1(), it.getT2()));
    }

    public Mono<Long> updateStateToDoneForOlderTasks(LocalDate date) {
        return reactiveMongoTemplate.updateMulti(
                        Query.query(Criteria.where("created").lte(date).and("state").is(TaskState.DOING)),
                        Update.update("state", TaskState.DONE),
                        Task.class)
                .map(UpdateResult::getModifiedCount);
    }


    private Mono<Page<Task>> execute(Task task, Example<Task> example, Pageable pageable) {
        return query(example,pageable,task)
                .flatMap(query -> mongoOperations.find(query, Task.class).collectList())
                .flatMap(tasks -> paginate(tasks, pageable, example));
    }

    private Mono<Page<Task>> paginate(List<Task> tasks, Pageable pageable, Example<Task> example){
        return Mono.just(tasks)
                .flatMap(it -> mongoOperations.count(Query.query(Criteria.byExample(example)), Task.class))
                .map(counter -> PageableExecutionUtils.getPage(tasks, pageable, () -> counter));
    }

    private Mono<Pageable> pageable(int page, int size){
        return Mono.just(PageRequest.of(page,size, Sort.by("title").ascending()));
    }

    private Mono<Example<Task>> queryExample(Task task){
        return Mono.just(task)
                .map(it -> ExampleMatcher.matching().withIgnorePaths("priority","state"))
                .map(matcher -> Example.of(task,matcher));
    }

    private Mono<Query> query(Example<Task> example, Pageable pageable, Task task){
        return Mono.just(example)
                .map(ex -> Query.query(Criteria.byExample(example)).with(pageable))
                .flatMap(query -> validatePriority(query, task))
                .flatMap(query -> validateState(query, task));
    }

    private Mono<Query> validatePriority(Query query, Task task) {
        return Mono.just(task)
                .filter(Task::isValidPriority)
                .map( it -> query.addCriteria(Criteria.where("priority").is(task.getPriority())))
                .defaultIfEmpty(query);
    }

    private Mono<Query> validateState(Query query, Task task){
        return Mono.just(task)
                .filter(Task::isValidState)
                .map(it -> query.addCriteria(Criteria.where("state").is(task.getState())))
                .defaultIfEmpty(query);
    }
}
