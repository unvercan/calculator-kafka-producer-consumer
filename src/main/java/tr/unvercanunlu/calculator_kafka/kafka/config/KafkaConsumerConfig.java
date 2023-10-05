package tr.unvercanunlu.calculator_kafka.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperandMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperationMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.ResultMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    private Map<String, Object> consumerConfigMap() {
        Map<String, Object> configMap = new HashMap<>();

        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "tr.unvercanunlu.calculator_kafka.kafka.message, tr.unvercanunlu.calculator_kafka.model.entity");

        return configMap;
    }

    @Bean
    public ConsumerFactory<String, OperandMessage> operandMessageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(this.consumerConfigMap());
    }

    @Bean
    public ConsumerFactory<String, OperationMessage> operationMessageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(this.consumerConfigMap());
    }

    @Bean
    public ConsumerFactory<String, ResultMessage> resultMessageConsumerFactory() {
        Map<String, Object> configMap = this.consumerConfigMap();

        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "tr.unvercanunlu.calculator_kafka.kafka.serde.ResultMessageJsonDeserializer");

        return new DefaultKafkaConsumerFactory<>(configMap);
    }

    @Bean(name = "operandMessageListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OperandMessage> operandMessageListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OperandMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.operandMessageConsumerFactory());
        return factory;
    }

    @Bean(name = "operationMessageListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OperationMessage> operationMessageListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OperationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.operationMessageConsumerFactory());
        return factory;
    }

    @Bean(name = "resultMessageListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ResultMessage> resultMessageListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ResultMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.resultMessageConsumerFactory());
        return factory;
    }

}
