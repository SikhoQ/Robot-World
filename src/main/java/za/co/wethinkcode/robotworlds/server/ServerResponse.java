package za.co.wethinkcode.robotworlds.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ServerResponse {
    String result;
    Map<String, Object> data;
    Map<String, Object> state;

    @JsonCreator
    public ServerResponse(@JsonProperty("robot") String result, @JsonProperty("command") Map<String, Object> data,
                          @JsonProperty("arguments") Map<String, Object> state) {
        this.result = result;
        this.data = data;
        this.state = state;
    }

    public String getResult() {
        return result;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Map<String, Object> getState() {
        return state;
    }
}
