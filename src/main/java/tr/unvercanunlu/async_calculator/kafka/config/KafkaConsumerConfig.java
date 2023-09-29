package tr.unvercanunlu.async_calculator.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import tr.unvercanunlu.async_calculator.model.entity.Operand;
import tr.unvercanunlu.async_calculator.model.entity.Result;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, Operand> operandConsumerFactory() {
        Map<String, Object> configMap = new HashMap<>();

        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(configMap);
    }

    @Bean
    public ConsumerFactory<String, Result> resultConsumerFactory() {
        Map<String, Object> configMap = new HashMap<>();

        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "tr.unvercanunlu.async_calculator.kafka.json.CustomResultJsonDeserializer");
        configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(configMap);
    }

    @Bean(name = "operandListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Operand> operandListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Operand> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(operandConsumerFactory());
        return factory;
    }

    @Bean(name = "resultListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Result> resultListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Result> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(resultConsumerFactory());
        return factory;
    }

}
