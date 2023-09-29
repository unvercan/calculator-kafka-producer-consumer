package tr.unvercanunlu.calculator_kafka.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.calculator_kafka.model.entity.Operand;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaOperandProducer implements IKafkaProducer<String, Operand> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final KafkaTemplate<String, Operand> operandKafkaTemplate;

    @Value(value = "${spring.kafka.topic.operand}")
    private String operandTopic;

    @Override
    public void send(String key, Operand value) {
        this.operandKafkaTemplate.send(this.operandTopic, key, value);

        this.logger.log(Level.INFO, () -> String.format("Operand is sent to '%s' topic. Key: %s, Value: %s", this.operandTopic, key, value));
    }

}
