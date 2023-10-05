package tr.unvercanunlu.calculator_kafka.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka.kafka.consumer.IKafkaConsumer;
import tr.unvercanunlu.calculator_kafka.kafka.message.ResultMessage;
import tr.unvercanunlu.calculator_kafka.model.entity.Calculation;
import tr.unvercanunlu.calculator_kafka.service.ICalculationService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaResultMessageConsumer implements IKafkaConsumer<UUID, ResultMessage> {

    private final ICalculationService calculationService;

    @SneakyThrows
    @Override
    @KafkaListener(topics = "${spring.kafka.topic.result}", containerFactory = "resultMessageListenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<UUID, ResultMessage> payload) {
        UUID calculationId = payload.key();
        ResultMessage message = payload.value();

        System.out.println(message + " is received from Kafka.");

        Calculation calculation = this.calculationService.get(calculationId);

        System.out.println(calculation + " is fetched from database.");

        calculation.setResultId(message.getResultId());
        calculation.setCompleted(true);

        System.out.println(calculation + " is updated.");

        this.calculationService.update(calculationId, calculation);

        System.out.println(calculation + " is saved to database.");

    }

}
