package za.co.wethinkcode.robotworlds.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * The ServerResponse class represents the response returned by the server
 * after executing a command. It contains the result, data, and state of the response.
 */
public class ServerResponse {
    private String result;
    private Map<String, Object> data;
    private Map<String, Object> state;

    /**
     * Constructs a ServerResponse object with the specified result and data.
     *
     * @param result The result of the command execution.
     * @param data The data related to the command execution.
     */
    public ServerResponse(String result, Map<String, Object> data) {
        this.result = result;
        this.data = data;
    }

    /**
     * Constructs a ServerResponse object with the specified result, data, and state.
     * This constructor is used for JSON deserialization.
     *
     * @param result The result of the command execution.
     * @param data The data related to the command execution.
     * @param state The state related to the command execution.
     */
    @JsonCreator
    public ServerResponse(@JsonProperty("robot") String result, @JsonProperty("command") Map<String, Object> data,
                          @JsonProperty("arguments") Map<String, Object> state) {
        this(result, data);
        this.state = state;
    }

    /**
     * Gets the result of the command execution.
     *
     * @return The result of the command execution.
     */
    public String getResult() {
        return result;
    }

    /**
     * Gets the data related to the command execution.
     *
     * @return The data related to the command execution.
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Gets the state related to the command execution.
     *
     * @return The state related to the command execution.
     */
    public Map<String, Object> getState() {
        return state;
    }
}

