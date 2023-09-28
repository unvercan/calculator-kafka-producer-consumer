package tr.unvercanunlu.sample.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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

    @SneakyThrows
    @Override
    @KafkaListener(topics = "${spring.kafka.topic.sample}", containerFactory = "sampleListenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<String, Sample> payload) {
        String key = payload.key();
        Sample value = payload.value();

        this.logger.log(Level.INFO, () -> String.format("Sample is received. Key: %s, Value: %s", key, value));

        Sum sum = Sum.builder()
                .id(value.getId())
                .third(value.getFirst() + value.getSecond())
                .build();

        this.sumRepository.save(sum);

        this.logger.log(Level.INFO, () -> String.format("Sum is created. Sum: %s", sum));
    }

}
