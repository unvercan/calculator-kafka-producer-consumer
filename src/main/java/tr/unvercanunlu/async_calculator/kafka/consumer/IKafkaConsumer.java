package tr.unvercanunlu.async_calculator.kafka.consumer;

import jakarta.validation.constraints.NotNull;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IKafkaConsumer<K, V> {

    void receive(@NotNull(message = "Payload should not be null.") ConsumerRecord<K, V> payload);

}
