package br.com.patrickriibeiro.tasks.repository;

import br.com.patrickriibeiro.tasks.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {
}
