package tr.unvercanunlu.calculator_kafka_producer_consumer.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import tr.unvercanunlu.calculator_kafka_producer_consumer.kafka.message.CalculationMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    public Map<String, Object> consumerConfigMap() {
        Map<String, Object> configMap = new HashMap<>();

        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "tr.unvercanunlu.calculator_kafka_producer_consumer.kafka.message, tr.unvercanunlu.calculator_kafka_producer_consumer.model.entity");

        return configMap;
    }

    @Bean
    public ConsumerFactory<String, CalculationMessage> calculationMessageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(this.consumerConfigMap());
    }

    @Bean(name = "calculationMessageListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, CalculationMessage> calculationMessageListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CalculationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.calculationMessageConsumerFactory());
        return factory;
    }
}
