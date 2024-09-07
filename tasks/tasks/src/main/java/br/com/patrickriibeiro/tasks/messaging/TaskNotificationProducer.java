package br.com.patrickriibeiro.tasks.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskNotificationProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskNotificationProducer.class);

    @Value("${kafka.task.notification.output}")
    private String topic;

    private final KafkaTemplate<Object,Object> template;

    public TaskNotificationProducer(KafkaTemplate<Object,Object> template){
        this.template = template;
    }

    

}
