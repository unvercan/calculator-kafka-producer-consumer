package tr.unvercanunlu.calculator_kafka.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperandMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperationMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.ResultMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SerdeConfig {

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

    private Map<String, Object> producerConfigMap() {
        Map<String, Object> configMap = new HashMap<>();

        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "tr.unvercanunlu.calculator_kafka.kafka.message, tr.unvercanunlu.calculator_kafka.model.entity");

        return configMap;
    }

    @Bean
    public JsonSerde<OperationMessage> operationMessageJsonSerde(ObjectMapper objectMapper) {
        JsonSerde<OperationMessage> serde = new JsonSerde<>(OperationMessage.class, objectMapper);
        serde.deserializer().configure(this.consumerConfigMap(), false);
        serde.serializer().configure(this.producerConfigMap(), false);
        return serde;
    }

    @Bean
    public JsonSerde<OperandMessage> operandMessageJsonSerde(ObjectMapper objectMapper) {
        JsonSerde<OperandMessage> serde = new JsonSerde<>(OperandMessage.class, objectMapper);
        serde.deserializer().configure(this.consumerConfigMap(), false);
        serde.serializer().configure(this.producerConfigMap(), false);
        return serde;
    }

    @Bean
    public JsonSerde<ResultMessage> resultMessageJsonSerde(ObjectMapper objectMapper) {
        JsonSerde<ResultMessage> serde = new JsonSerde<>(ResultMessage.class, objectMapper);
        serde.deserializer().configure(this.consumerConfigMap(), false);
        serde.serializer().configure(this.producerConfigMap(), false);
        return serde;
    }

}
