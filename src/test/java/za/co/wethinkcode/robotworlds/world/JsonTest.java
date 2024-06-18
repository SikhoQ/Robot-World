package za.co.wethinkcode.robotworlds.world;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    @Test
    public void toJsonDoesNotThrowExceptionTest(){
        assertDoesNotThrow(()-> JsonUtility.toJson(123));
    }

    @Test
    public void toJsonTest() throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        ServerResponse response = new ServerResponse("success", Map.of("key1", "value1"), Map.of("key2", "value2"));
        String jsonString = objMapper.writeValueAsString(response);

        assertEquals(jsonString, JsonUtility.toJson(response));
    }
}
