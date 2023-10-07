package tr.unvercanunlu.calculator_kafka_prod_cons.kafka.producer.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(KafkaCalculationProducer.class);

    private final KafkaTemplate<String, CalculationMessage> calculationMessageKafkaTemplate;

    @Value(value = "${spring.kafka.topic.calculation}")
    private String calculationTopic;

    @Override
    public void send(UUID key, Calculation value) {
        this.logger.info("Kafka producer is started.");

        CalculationMessage message = CalculationMessage.builder()
                .first(value.getFirst())
                .second(value.getSecond())
                .operationCode(value.getOperationCode())
                .build();

        this.logger.info("Calculation message is created.");

        this.logger.debug("Created calculation message: " + message);

        this.calculationMessageKafkaTemplate.send(this.calculationTopic, key.toString(), message);

        this.logger.info("Kafka producer sent calculation message with " + key + " key as a record to " + this.calculationTopic + " topic.");

        this.logger.info("Kafka producer is end.");
    }
}
