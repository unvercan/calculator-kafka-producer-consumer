package tr.unvercanunlu.calculator_kafka_producer_consumer.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka_producer_consumer.kafka.consumer.IKafkaConsumer;
import tr.unvercanunlu.calculator_kafka_producer_consumer.kafka.message.CalculationMessage;
import tr.unvercanunlu.calculator_kafka_producer_consumer.service.ICalculationService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaCalculationConsumer implements IKafkaConsumer<String, CalculationMessage> {

    private final Logger logger = LoggerFactory.getLogger(KafkaCalculationConsumer.class);

    private final ICalculationService calculationService;

    @Value(value = "${spring.kafka.topic.calculation}")
    private String calculationTopic;

    @Override
    @KafkaListener(topics = "${spring.kafka.topic.calculation}", containerFactory = "calculationMessageListenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<String, CalculationMessage> payload) {
        this.logger.info("Kafka Calculation Consumer is started.");

        this.logger.info("Kafka Calculation Consumer consumed a new record from '" + this.calculationTopic + "' Kafka Topic.");

        String key = payload.key();
        CalculationMessage value = payload.value();

        this.logger.debug("Consumed record key: '" + key + "'");
        this.logger.debug("Consumed record value: " + value);

        UUID calculationId = UUID.fromString(key);

        Double result = switch (value.getOperationCode()) {
            case 0 -> (double) value.getFirst() + (double) value.getSecond();
            case 1 -> (double) value.getFirst() - (double) value.getSecond();
            case 2 -> (double) value.getFirst() * (double) value.getSecond();
            case 3 -> (double) value.getFirst() / (double) value.getSecond();
            case 4 -> (double) value.getFirst() % (double) value.getSecond();
            case 5 -> Math.pow(value.getFirst(), value.getSecond());
            case 6 -> ((double) value.getFirst() + (double) value.getSecond()) / 2;
            case 7 -> (double) Math.max(value.getFirst(), value.getSecond());
            case 8 -> (double) Math.min(value.getFirst(), value.getSecond());
            default -> null;
        };

        this.logger.info("Calculation with '" + calculationId + "' id is done.");

        this.calculationService.setResult(calculationId, result);

        this.calculationService.setCompleteness(calculationId, Boolean.TRUE);

        this.logger.info("Calculation with '" + calculationId + "' id is updated.");

        this.logger.info("Kafka Calculation Consumer is end.");
    }
}
