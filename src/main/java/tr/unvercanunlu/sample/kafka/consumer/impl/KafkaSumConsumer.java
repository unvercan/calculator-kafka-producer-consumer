package tr.unvercanunlu.sample.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.sample.kafka.consumer.IKafkaConsumer;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaSumConsumer implements IKafkaConsumer<String, Sum> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Value(value = "${spring.kafka.topic.sum}")
    private String sumTopic;

    @SneakyThrows
    @Override
    @KafkaListener(topics = "${spring.kafka.topic.sum}", containerFactory = "sumListenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<String, Sum> payload) {
        String key = payload.key();
        Sum value = payload.value();

        this.logger.log(Level.INFO, () -> String.format("Sum is received from '%s' topic. Key: %s, Value: %s", this.sumTopic, key, value));
    }

}
