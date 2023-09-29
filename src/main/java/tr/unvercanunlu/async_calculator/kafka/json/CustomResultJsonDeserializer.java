package tr.unvercanunlu.async_calculator.kafka.json;

import org.apache.kafka.common.serialization.Deserializer;
import org.json.JSONObject;
import tr.unvercanunlu.async_calculator.model.entity.Result;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomResultJsonDeserializer implements Deserializer<Result> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Result deserialize(String topic, byte[] bytes) {
        String json = new String(bytes, StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(json);
        String idString = jsonObject.getString("id");
        UUID id = UUID.fromString(idString);
        Double value = jsonObject.getDouble("value");

        Result result = Result.builder()
                .id(id)
                .value(value)
                .build();

        this.logger.log(Level.INFO, () -> String.format("%s JSON is converted to %s object.", json, result));

        return result;
    }

}
