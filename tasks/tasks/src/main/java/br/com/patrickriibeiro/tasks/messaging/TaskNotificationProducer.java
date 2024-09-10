package br.com.patrickriibeiro.tasks.messaging;

import br.com.patrickriibeiro.tasks.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TaskNotificationProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskNotificationProducer.class);

    private final String topic;

    private final KafkaTemplate<Object,Object> template;

    public TaskNotificationProducer(@Value("${kafka.task.notification.output}") String topic, KafkaTemplate<Object,Object> template){
        this.topic = topic;
        this.template = template;
    }

    public Mono<Task> sendNotification(Task task){
        return Mono.just(task)
                .map(it -> this.template.send(topic,task))
                .map(it -> task)
                .doOnNext(it -> LOGGER.info("Task notification send successfully"));
    }
    

}
