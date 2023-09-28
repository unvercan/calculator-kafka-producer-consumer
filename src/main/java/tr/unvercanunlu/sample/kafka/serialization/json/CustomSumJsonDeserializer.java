package tr.unvercanunlu.sample.kafka.serialization.json;

import org.apache.kafka.common.serialization.Deserializer;
import org.json.JSONObject;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.nio.charset.StandardCharsets;

public class CustomSumJsonDeserializer implements Deserializer<Sum> {

    @Override
    public Sum deserialize(String s, byte[] bytes) {
        String jsonString = new String(bytes, StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(jsonString);
        Integer third = jsonObject.getInt("third");

        return Sum.builder().third(third).build();
    }

}
