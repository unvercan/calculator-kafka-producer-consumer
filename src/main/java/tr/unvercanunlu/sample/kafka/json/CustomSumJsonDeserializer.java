package tr.unvercanunlu.sample.kafka.json;

import org.apache.kafka.common.serialization.Deserializer;
import org.json.JSONObject;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomSumJsonDeserializer implements Deserializer<Sum> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Sum deserialize(String topic, byte[] bytes) {
        String json = new String(bytes, StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(json);
        String idString = jsonObject.getString("id");
        UUID id = UUID.fromString(idString);
        Integer third = jsonObject.getInt("third");

        Sum sum = Sum.builder()
                .id(id)
                .third(third)
                .build();

        this.logger.log(Level.INFO, () -> String.format("%s JSON is converted to %s object.", json, sum));

        return sum;
    }

}
