package za.co.wethinkcode.robotworlds.server;

import java.util.Map;

public class ServerResponse {
    String result;
    Map<String, Object> data;
    Map<String, Object> state;

    // provide the data as a 2D array of 2 items
    // type-cast the 2nd item in the constructors
    // or create new correct instance and copy contents before passing on call
    public ServerResponse(String result, Map<String, Object> data) {
        this.result = result;
        this.data = data;
    }

    public ServerResponse(String result, Map<String, Object> data,
                          Map<String, Object> state) {
        this(result, data);
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

