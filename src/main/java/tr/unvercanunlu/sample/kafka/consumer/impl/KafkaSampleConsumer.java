package tr.unvercanunlu.sample.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.sample.kafka.consumer.IKafkaConsumer;
import tr.unvercanunlu.sample.model.entity.Sample;
import tr.unvercanunlu.sample.model.entity.Sum;
import tr.unvercanunlu.sample.repository.ISumRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaSampleConsumer implements IKafkaConsumer<String, Sample> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final ISumRepository sumRepository;

    @Value(value = "${spring.kafka.topic.sample}")
    private String sampleTopic;

    @SneakyThrows
    @Override
    @KafkaListener(topics = "${spring.kafka.topic.sample}", containerFactory = "sampleListenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<String, Sample> payload) {
        String key = payload.key();
        Sample value = payload.value();

        this.logger.log(Level.INFO, () -> String.format("Sample is received from '%s' topic. Key: %s, Value: %s", this.sampleTopic, key, value));

        Sum sum = Sum.builder()
                .id(value.getId())
                .third(value.getFirst() + value.getSecond())
                .build();

        this.logger.log(Level.INFO, String.format("Sum is created. %s", sum));

        sum = this.sumRepository.save(sum);

        this.logger.log(Level.INFO, String.format("Sum is saved to NoSQL database. %s", sum));
    }

}
