package tr.unvercanunlu.sample.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.sample.kafka.consumer.IKafkaConsumer;
import tr.unvercanunlu.sample.model.entity.Sample;

@Component
@RequiredArgsConstructor
public class KafkaConsumer implements IKafkaConsumer<String, Sample> {

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
    }

}
