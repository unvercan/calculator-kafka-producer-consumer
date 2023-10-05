package tr.unvercanunlu.calculator_kafka_prod_cons.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka_prod_cons.kafka.consumer.IKafkaConsumer;
import tr.unvercanunlu.calculator_kafka_prod_cons.kafka.message.CalculationMessage;
import tr.unvercanunlu.calculator_kafka_prod_cons.model.entity.Calculation;
import tr.unvercanunlu.calculator_kafka_prod_cons.model.entity.Operation;
import tr.unvercanunlu.calculator_kafka_prod_cons.repository.ICalculationRepository;
import tr.unvercanunlu.calculator_kafka_prod_cons.repository.IOperationRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaCalculationMessageConsumer implements IKafkaConsumer<String, CalculationMessage> {

    private final ICalculationRepository calculationRepository;

    private final IOperationRepository operationRepository;

    @SneakyThrows
    @Override
    @KafkaListener(topics = "${spring.kafka.topic.calculation}", containerFactory = "calculationMessageListenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<String, CalculationMessage> payload) {
        String key = payload.key();
        UUID calculationId = UUID.fromString(key);
        CalculationMessage value = payload.value();

        System.out.println(value + " is received from Kafka.");

        Operation operation = this.operationRepository.findById(value.getOperationCode())
                .orElseThrow(() -> new RuntimeException("Operation with " + value.getOperationCode() + " code is not found in the database."));

        System.out.println(operation + " is fetched from database.");

        Calculation calculation = this.calculationRepository.findById(calculationId)
                .orElseThrow(() -> new RuntimeException("Calculation with " + calculationId + " id is not found in the database."));

        System.out.println(calculation + " is fetched from database.");

        Double result;

        try {
            result = switch (value.getOperationCode()) {
                case 0 -> (double) value.getFirst() + value.getSecond();
                case 1 -> (double) value.getFirst() - value.getSecond();
                case 2 -> (double) value.getFirst() * value.getSecond();
                case 3 -> (double) value.getFirst() / value.getSecond();
                case 4 -> (double) value.getFirst() % value.getSecond();
                case 5 -> Math.pow(value.getFirst(), value.getSecond());
                case 6 -> (double) (value.getFirst() + value.getSecond()) / 2;
                case 7 -> (double) Math.max(value.getFirst(), value.getSecond());
                case 8 -> (double) Math.min(value.getFirst(), value.getSecond());
                default -> null;
            };
        } catch (Exception e) {
            throw new RuntimeException("Error happens when calculation.");
        }

        calculation.setResult(result);

        System.out.println(calculation + " is updated.");

        calculation = this.calculationRepository.save(calculation);

        System.out.println(calculation + " is saved to database.");
    }
}
