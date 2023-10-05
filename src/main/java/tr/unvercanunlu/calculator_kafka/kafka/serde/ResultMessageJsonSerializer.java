package tr.unvercanunlu.calculator_kafka.kafka.serde;

import org.apache.kafka.common.serialization.Serializer;
import org.json.JSONObject;
import tr.unvercanunlu.calculator_kafka.kafka.message.ResultMessage;

import java.nio.charset.StandardCharsets;

public class ResultMessageJsonSerializer implements Serializer<ResultMessage> {

    @Override
    public byte[] serialize(String topic, ResultMessage message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultId", message.getResultId());
        jsonObject.put("calculationId", message.getCalculationId());
        jsonObject.put("value", message.getValue());

        String json = jsonObject.toString();

        return json.getBytes(StandardCharsets.UTF_8);
    }

}
