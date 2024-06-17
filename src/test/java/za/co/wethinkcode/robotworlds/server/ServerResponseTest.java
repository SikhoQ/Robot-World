package za.co.wethinkcode.robotworlds.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ServerResponseTest {

    @Test
    public void testServerResponseInitialization() throws Exception {
        String result = "success";
        Map<String, Object> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");

        Map<String, Object> state = new HashMap<>();
        state.put("state1", 100);
        state.put("state2", "active");

        ObjectMapper objectMapper = new ObjectMapper();

        String json = String.format("{\"robot\": \"%s\", \"command\": %s, \"arguments\": %s}",
                result, objectMapper.writeValueAsString(data), objectMapper.writeValueAsString(state));

        ServerResponse serverResponse = objectMapper.readValue(json, ServerResponse.class);

        assertNotNull(serverResponse);
        assertEquals(result, serverResponse.getResult());
        assertEquals(data, serverResponse.getData());
        assertEquals(state, serverResponse.getState());
    }
}
