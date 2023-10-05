package tr.unvercanunlu.calculator_kafka.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperandMessage;
import tr.unvercanunlu.calculator_kafka.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.calculator_kafka.model.entity.Operand;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaOperandProducer implements IKafkaProducer<UUID, Operand> {

    private final KafkaTemplate<String, OperandMessage> operandMessageKafkaTemplate;

    @Value(value = "${spring.kafka.topic.operand}")
    private String operandTopic;

    @Override
    public void send(UUID calculationId, Operand operand) {
        OperandMessage message = OperandMessage.builder()
                .calculationId(calculationId)
                .operandId(operand.getId())
                .first(operand.getFirst())
                .second(operand.getSecond())
                .build();

        System.out.println(message + " is created.");

        this.operandMessageKafkaTemplate.send(this.operandTopic, calculationId.toString(), message);

        System.out.println(message + " is sent to Kafka.");

    }

}
