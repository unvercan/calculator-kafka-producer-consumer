package tr.unvercanunlu.calculator_kafka_prod_cons.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka_prod_cons.kafka.consumer.IKafkaConsumer;
import tr.unvercanunlu.calculator_kafka_prod_cons.kafka.message.CalculationMessage;
import tr.unvercanunlu.calculator_kafka_prod_cons.model.entity.Calculation;
import tr.unvercanunlu.calculator_kafka_prod_cons.model.entity.Operation;
import tr.unvercanunlu.calculator_kafka_prod_cons.repository.ICalculationRepository;
import tr.unvercanunlu.calculator_kafka_prod_cons.repository.IOperationRepository;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaCalculationMessageConsumer implements IKafkaConsumer<String, CalculationMessage> {

    private final Logger logger = LoggerFactory.getLogger(KafkaCalculationMessageConsumer.class);

    private final ICalculationRepository calculationRepository;

    private final IOperationRepository operationRepository;

    @Value(value = "${spring.kafka.topic.calculation}")
    private String calculationTopic;

    @Override
    @KafkaListener(topics = "${spring.kafka.topic.calculation}", containerFactory = "calculationMessageListenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<String, CalculationMessage> payload) {
        this.logger.info("Kafka consumer is started.");

        this.logger.info("Kafka consumer received new record from " + this.calculationTopic + " topic.");

        String key = payload.key();
        CalculationMessage value = payload.value();

        this.logger.debug("Received record key: " + key);
        this.logger.debug("Received record value: " + value);

        UUID calculationId = UUID.fromString(key);

        Optional<Operation> optionalOperation = this.operationRepository.findById(value.getOperationCode());

        if (optionalOperation.isEmpty()) {
            this.logger.info("Operation with " + value.getOperationCode() + " code is not found in the database.");

            throw new RuntimeException("Data is missing");
        }

        this.logger.info("Operation with " + value.getOperationCode() + " code  is fetched from the database.");

        Operation operation = optionalOperation.get();

        this.logger.debug("Fetched operation: " + operation);


        Optional<Calculation> optionalCalculation = this.calculationRepository.findById(calculationId);

        if (optionalCalculation.isEmpty()) {
            this.logger.info("Calculation with " + calculationId + " id is not found in the database.");

            throw new RuntimeException("Data is missing");
        }

        this.logger.info("Calculation with " + calculationId + " id is fetched from the database.");

        Calculation calculation = optionalCalculation.get();

        this.logger.debug("Fetched calculation: " + calculation);

        if (!value.getFirst().equals(calculation.getFirst())
                || !value.getSecond().equals(calculation.getSecond())
                || !value.getOperationCode().equals(calculation.getOperationCode())) {

            this.logger.info("Calculation with " + calculationId + " id is different from the message.");

            throw new RuntimeException("Data is inconsistent.");
        }

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

        this.logger.info("Calculation with " + calculationId + " id is done.");

        calculation.setResult(result);

        this.logger.info("Calculation with " + calculationId + " id is updated.");

        this.logger.debug("Updated calculation: " + calculation);

        calculation = this.calculationRepository.save(calculation);

        this.logger.info("Calculation with " + calculationId + " id is saved to database.");

        this.logger.debug("Saved calculation: " + calculation);

        this.logger.info("Kafka consumer is end.");
    }
}
