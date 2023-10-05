package tr.unvercanunlu.calculator_kafka.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperationMessage;
import tr.unvercanunlu.calculator_kafka.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.calculator_kafka.model.entity.Operation;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaOperationProducer implements IKafkaProducer<UUID, Operation> {

    private final KafkaTemplate<String, OperationMessage> operationMessageKafkaTemplate;

    @Value(value = "${spring.kafka.topic.operation}")
    private String operationTopic;

    @Override
    public void send(UUID calculationId, Operation operation) {
        OperationMessage message = OperationMessage.builder()
                .calculationId(calculationId)
                .code(operation.getCode())
                .build();

        System.out.println(message + " is created.");

        this.operationMessageKafkaTemplate.send(this.operationTopic, calculationId.toString(), message);

        System.out.println(message + " is sent to Kafka.");

    }

}
