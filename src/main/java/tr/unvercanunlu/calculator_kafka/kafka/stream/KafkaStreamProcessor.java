package tr.unvercanunlu.calculator_kafka.kafka.stream;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka.kafka.message.Message;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperandMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperationMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.ResultMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaStreamProcessor {

    private final ValueJoinerWithKey<String, OperandMessage, OperationMessage, ResultMessage> operandOperationJoiner;

    private final JsonSerde<OperationMessage> operationMessageJsonSerde;

    private final JsonSerde<OperandMessage> operandMessageJsonSerde;

    private final JsonSerde<ResultMessage> resultMessageJsonSerde;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @Value(value = "${spring.kafka.topic.operand}")
    private String operandTopic;

    @Value(value = "${spring.kafka.topic.operation}")
    private String operationTopic;

    @Value(value = "${spring.kafka.topic.result}")
    private String resultTopic;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamConfig() {
        JsonSerde<Message> jsonSerde = new JsonSerde<>(Message.class);

        Map<String, Object> configMap = new HashMap<>();

        configMap.put(StreamsConfig.APPLICATION_ID_CONFIG, "calculator_kafka");
        configMap.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        configMap.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        configMap.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, jsonSerde.getClass());
        configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "tr.unvercanunlu.calculator_kafka.kafka.message, tr.unvercanunlu.calculator_kafka.model.entity");

        return new KafkaStreamsConfiguration(configMap);
    }


    /*
    private Serde<OperandMessage> operandMessageSerde() {
        Map<String, Object> serdeConfigMap = new HashMap<>();
        serdeConfigMap.put("JsonPOJOClass", OperandMessage.class);

        JsonSerializer<OperandMessage> operandMessageSerializer = new JsonSerializer<>();
        operandMessageSerializer.configure(serdeConfigMap, false);

        JsonDeserializer<OperandMessage> operandMessageDeserializer = new JsonDeserializer<>();
        operandMessageDeserializer.configure(serdeConfigMap, false);

        return Serdes.serdeFrom(operandMessageSerializer, operandMessageDeserializer);
    }

    private Serde<OperationMessage> operationMessageSerde() {
        Map<String, Object> serdeConfigMap = new HashMap<>();
        serdeConfigMap.put("JsonPOJOClass", OperationMessage.class);

        JsonSerializer<OperationMessage> operationMessageSerializer = new JsonSerializer<>();
        operationMessageSerializer.configure(serdeConfigMap, false);

        JsonDeserializer<OperationMessage> operationMessageDeserializer = new JsonDeserializer<>();
        operationMessageDeserializer.configure(serdeConfigMap, false);

        return Serdes.serdeFrom(operationMessageSerializer, operationMessageDeserializer);
    }

    private Serde<ResultMessage> resultMessageSerde() {
        Map<String, Object> serdeConfigMap = new HashMap<>();
        serdeConfigMap.put("JsonPOJOClass", ResultMessage.class);

        JsonSerializer<ResultMessage> resultMessageSerializer = new JsonSerializer<>();
        resultMessageSerializer.configure(serdeConfigMap, false);

        JsonDeserializer<ResultMessage> resultMessageDeserializer = new JsonDeserializer<>();
        resultMessageDeserializer.configure(serdeConfigMap, false);

        return Serdes.serdeFrom(resultMessageSerializer, resultMessageDeserializer);
    }
     */

    private KStream<String, OperandMessage> operandMessageStream(StreamsBuilder streamsBuilder) {
        return streamsBuilder
                .stream(this.operandTopic, Consumed.with(Serdes.String(), this.operandMessageJsonSerde))
                .filter((key, value) -> Objects.nonNull(key) && Objects.nonNull(value))
                .peek((key, value) -> System.out.println("Key: " + key + " , Value: " + value))
                .map(KeyValue::new);
    }

    private KStream<String, OperationMessage> operationMessageStream(StreamsBuilder streamsBuilder) {
        return streamsBuilder
                .stream(this.operationTopic, Consumed.with(Serdes.String(), this.operationMessageJsonSerde))
                .filter((key, value) -> Objects.nonNull(key) && Objects.nonNull(value))
                .peek((key, value) -> System.out.println("Key: " + key + " , Value: " + value))
                .map(KeyValue::new);
    }

    @Bean
    public KStream<String, ResultMessage> resultMessageStream(StreamsBuilder streamsBuilder) {
        KStream<String, OperandMessage> operandMessageStream = this.operandMessageStream(streamsBuilder);

        KStream<String, OperationMessage> operationMessageStream = this.operationMessageStream(streamsBuilder);
        KTable<String, OperationMessage> operationMessageTable = operationMessageStream.toTable();

        KStream<String, ResultMessage> resultMessageStream = operandMessageStream.join(operationMessageTable, this.operandOperationJoiner);

        resultMessageStream = resultMessageStream.
                filter((key, value) -> Objects.nonNull(key) && Objects.nonNull(value))
                .peek((key, value) -> System.out.println("Key: " + key + " , Value: " + value))
                .map(KeyValue::new);

        resultMessageStream.to(this.resultTopic, Produced.with(Serdes.String(), this.resultMessageJsonSerde));

        return resultMessageStream;
    }

}
