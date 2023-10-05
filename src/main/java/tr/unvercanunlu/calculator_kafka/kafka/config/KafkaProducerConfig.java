package tr.unvercanunlu.calculator_kafka.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperandMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperationMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.ResultMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    private Map<String, Object> producerConfigMap() {
        Map<String, Object> configMap = new HashMap<>();

        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "tr.unvercanunlu.calculator_kafka.kafka.message, tr.unvercanunlu.calculator_kafka.model.entity");

        return configMap;
    }

    @Bean
    public ProducerFactory<String, OperandMessage> operandMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(this.producerConfigMap());
    }

    @Bean
    public ProducerFactory<String, OperationMessage> operationMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(this.producerConfigMap());
    }

    @Bean
    public ProducerFactory<String, ResultMessage> resultMessageProducerFactory() {
        Map<String, Object> configMap = this.producerConfigMap();

        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "tr.unvercanunlu.calculator_kafka.kafka.serde.ResultMessageJsonSerializer");

        return new DefaultKafkaProducerFactory<>(configMap);
    }

    @Bean
    public KafkaTemplate<String, OperandMessage> operandMessageKafkaTemplate() {
        return new KafkaTemplate<>(this.operandMessageProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, OperationMessage> operationMessageKafkaTemplate() {
        return new KafkaTemplate<>(operationMessageProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, ResultMessage> resultMessageKafkaTemplate() {
        return new KafkaTemplate<>(resultMessageProducerFactory());
    }

}
