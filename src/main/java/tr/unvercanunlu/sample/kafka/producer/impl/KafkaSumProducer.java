package tr.unvercanunlu.sample.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.sample.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaSumProducer implements IKafkaProducer<String, Sum> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final KafkaTemplate<String, Sum> sumKafkaTemplate;

    @Value(value = "${spring.kafka.topic.sum}")
    private String sumTopic;

    @Override
    public void send(String key, Sum value) {
        this.sumKafkaTemplate.send(this.sumTopic, key, value);

        this.logger.log(Level.INFO, () -> String.format("Sum is sent to '%s' topic. Key: %s, Value: %s", this.sumTopic, key, value));
    }

}
