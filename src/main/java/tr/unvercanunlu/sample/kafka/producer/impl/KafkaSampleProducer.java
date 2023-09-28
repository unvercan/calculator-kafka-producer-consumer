package tr.unvercanunlu.sample.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.sample.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.sample.model.entity.Sample;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaSampleProducer implements IKafkaProducer<String, Sample> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final KafkaTemplate<String, Sample> sampleKafkaTemplate;

    @Value(value = "${spring.kafka.topic.sample}")
    private String sampleTopic;

    @Override
    public void send(String key, Sample value) {
        this.sampleKafkaTemplate.send(this.sampleTopic, key, value);

        this.logger.log(Level.INFO, () -> String.format("Sample is sent. Key: %s, Value: %s", key, value));
    }

}
