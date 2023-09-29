package tr.unvercanunlu.async_calculator.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.async_calculator.kafka.consumer.IKafkaConsumer;
import tr.unvercanunlu.async_calculator.model.entity.Operand;
import tr.unvercanunlu.async_calculator.model.entity.Result;
import tr.unvercanunlu.async_calculator.repository.IResultRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class KafkaOperandConsumer implements IKafkaConsumer<String, Operand> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final IResultRepository resultRepository;

    @Value(value = "${spring.kafka.topic.operand}")
    private String operandTopic;

    @SneakyThrows
    @Override
    @KafkaListener(topics = "${spring.kafka.topic.operand}", containerFactory = "operandListenerFactory", groupId = "${spring.kafka.group-id}")
    public void receive(ConsumerRecord<String, Operand> payload) {
        String key = payload.key();
        Operand value = payload.value();

        this.logger.log(Level.INFO, () -> String.format("Operand is received from '%s' topic. Key: %s, Value: %s", this.operandTopic, key, value));

        Result result = Result.builder()
                .id(value.getId())
                .value(value.getFirst().doubleValue() + value.getSecond().doubleValue())
                .build();

        this.logger.log(Level.INFO, String.format("Result is created. %s", result));

        result = this.resultRepository.save(result);

        this.logger.log(Level.INFO, String.format("Result is saved to NoSQL database. %s", result));
    }

}
