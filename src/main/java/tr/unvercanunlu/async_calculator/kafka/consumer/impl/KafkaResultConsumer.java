package tr.unvercanunlu.async_calculator.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.async_calculator.kafka.consumer.IKafkaConsumer;
import tr.unvercanunlu.async_calculator.model.entity.Result;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaResultConsumer implements IKafkaConsumer<String, Result> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Value(value = "${spring.kafka.topic.result}")
    private String resultTopic;

    @SneakyThrows
    @Override
    @KafkaListener(topics = "${spring.kafka.topic.result}", containerFactory = "resultListenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<String, Result> payload) {
        String key = payload.key();
        Result value = payload.value();

        this.logger.log(Level.INFO, () -> String.format("Result is received from '%s' topic. Key: %s, Value: %s", this.resultTopic, key, value));
    }

}
