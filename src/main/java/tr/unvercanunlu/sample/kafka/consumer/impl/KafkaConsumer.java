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

@Component
@RequiredArgsConstructor
public class KafkaConsumer implements IKafkaConsumer<String, Sample> {

    private final ISumRepository sumRepository;

    @SneakyThrows
    @Override
    @KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "listenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<String, Sample> payload) {
        String key = payload.key();
        Sample value = payload.value();

        System.out.println("Sample is received. " +
                "Key: " + key + ". " +
                "Value: " + "Sample{first=" + value.getFirst() + " , second=" + value.getSecond() + "}"
        );

        Sum sum = Sum.builder()
                .id(value.getId())
                .third(value.getFirst() + value.getSecond())
                .build();

        this.sumRepository.save(sum);
    }

}
