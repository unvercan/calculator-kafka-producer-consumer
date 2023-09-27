package tr.unvercanunlu.sample.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.sample.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.sample.model.entity.Sum;

@Component
@RequiredArgsConstructor
public class KafkaSumProducer implements IKafkaProducer<String, Sum> {

    private final KafkaTemplate<String, Sum> sumKafkaTemplate;

    @Override
    public void send(String topic, String key, Sum value) {
        this.sumKafkaTemplate.send(topic, key, value);

        System.out.println("Sum is sent. " +
                "Key: " + key + ". " +
                "Value: " + "Sum{third=" + value.getThird() + "}"
        );
    }

}
