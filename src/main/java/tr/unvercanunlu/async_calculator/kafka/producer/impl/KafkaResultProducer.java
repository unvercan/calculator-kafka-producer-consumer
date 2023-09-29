package tr.unvercanunlu.async_calculator.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.async_calculator.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.async_calculator.model.entity.Result;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaResultProducer implements IKafkaProducer<String, Result> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final KafkaTemplate<String, Result> resultKafkaTemplate;

    @Value(value = "${spring.kafka.topic.result}")
    private String resultTopic;

    @Override
    public void send(String key, Result value) {
        this.resultKafkaTemplate.send(this.resultTopic, key, value);

        this.logger.log(Level.INFO, () -> String.format("Result is sent to '%s' topic. Key: %s, Value: %s", this.resultTopic, key, value));
    }

}
