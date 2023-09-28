package tr.unvercanunlu.sample.kafka.serialization.json;

import org.apache.kafka.common.serialization.Serializer;
import org.json.JSONObject;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomSumJsonSerializer implements Serializer<Sum> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public byte[] serialize(String topic, Sum sum) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", sum.getId());
        jsonObject.put("third", sum.getThird());

        String json = jsonObject.toString();

        this.logger.log(Level.INFO, () -> String.format("%s object is converted to %s JSON.", sum, json));

        return json.getBytes(StandardCharsets.UTF_8);
    }

}
