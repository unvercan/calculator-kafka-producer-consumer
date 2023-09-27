package tr.unvercanunlu.sample.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.sample.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.sample.model.entity.Sample;

@Component
@RequiredArgsConstructor
public class KafkaSampleProducer implements IKafkaProducer<String, Sample> {

    private final KafkaTemplate<String, Sample> sampleKafkaTemplate;

    @Override
    public void send(String topic, String key, Sample value) {
        this.sampleKafkaTemplate.send(topic, key, value);

        System.out.println("Sample is sent. " +
                "Key: " + key + ". " +
                "Value: " + "Sample{first=" + value.getFirst() + " , second=" + value.getSecond() + "}"
        );
    }

}
