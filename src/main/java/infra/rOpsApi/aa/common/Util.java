package infra.rOpsApi.aa.common;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Component;

@Component
public class Util {
    public String getValueByKey(String jsonString, String key) {
        JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
        String result = jsonObject.get(key).toString();

        return result;
    }
}
