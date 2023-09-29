package tr.unvercanunlu.calculator_kafka.kafka.json;

import org.apache.kafka.common.serialization.Serializer;
import org.json.JSONObject;
import tr.unvercanunlu.calculator_kafka.model.entity.Result;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomResultJsonSerializer implements Serializer<Result> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public byte[] serialize(String topic, Result result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", result.getId());
        jsonObject.put("value", result.getValue());

        String json = jsonObject.toString();

        this.logger.log(Level.INFO, () -> String.format("%s object is converted to %s JSON.", result, json));

        return json.getBytes(StandardCharsets.UTF_8);
    }

}
