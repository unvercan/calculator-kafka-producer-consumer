package tr.unvercanunlu.calculator_kafka.kafka.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.json.JSONObject;
import tr.unvercanunlu.calculator_kafka.kafka.message.ResultMessage;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ResultMessageJsonDeserializer implements Deserializer<ResultMessage> {

    @Override
    public ResultMessage deserialize(String topic, byte[] bytes) {
        String json = new String(bytes, StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(json);
        String resultIdString = jsonObject.getString("resultId");
        UUID resultId = UUID.fromString(resultIdString);
        String calculationIdString = jsonObject.getString("calculationId");
        UUID calculationId = UUID.fromString(calculationIdString);
        Double value = jsonObject.getDouble("value");

        return ResultMessage.builder()
                .resultId(resultId)
                .calculationId(calculationId)
                .value(value)
                .build();
    }

}
