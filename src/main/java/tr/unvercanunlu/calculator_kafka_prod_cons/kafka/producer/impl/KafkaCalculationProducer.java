package tr.unvercanunlu.calculator_kafka_prod_cons.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka_prod_cons.kafka.message.CalculationMessage;
import tr.unvercanunlu.calculator_kafka_prod_cons.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.calculator_kafka_prod_cons.model.entity.Calculation;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaCalculationProducer implements IKafkaProducer<UUID, Calculation> {

    private final KafkaTemplate<String, CalculationMessage> calculationMessageKafkaTemplate;

    @Value(value = "${spring.kafka.topic.calculation}")
    private String calculationTopic;

    @Override
    public void send(UUID key, Calculation value) {
        CalculationMessage message = CalculationMessage.builder()
                .first(value.getFirst())
                .second(value.getSecond())
                .operationCode(value.getOperationCode())
                .build();

        System.out.println(message + " is created.");

        this.calculationMessageKafkaTemplate.send(this.calculationTopic, key.toString(), message);

        System.out.println(message + " is sent to Kafka.");

    }
}
