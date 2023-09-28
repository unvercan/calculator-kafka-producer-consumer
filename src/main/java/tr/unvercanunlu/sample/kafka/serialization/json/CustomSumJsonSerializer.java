package tr.unvercanunlu.sample.kafka.serialization.json;

import org.apache.kafka.common.serialization.Serializer;
import org.json.JSONObject;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.nio.charset.StandardCharsets;

public class CustomSumJsonSerializer implements Serializer<Sum> {

    @Override
    public byte[] serialize(String s, Sum sum) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("third", sum.getThird());

        String jsonString = jsonObject.toString();

        return jsonString.getBytes(StandardCharsets.UTF_8);
    }

}
