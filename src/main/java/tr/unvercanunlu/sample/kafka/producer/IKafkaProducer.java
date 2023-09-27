package tr.unvercanunlu.sample.kafka.producer;

public interface IKafkaProducer<K, V> {

    void send(String topic, K key, V value);

}
