package tr.unvercanunlu.sample.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IKafkaConsumer<K, V> {

    void receive(ConsumerRecord<K, V> payload);

}
