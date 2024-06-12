package za.co.wethinkcode.robotworlds.world;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class JsonTest {

    @Test
    public void toJsonDoesNotThrowExceptionTest(){
        Json json = new Json();

        assertDoesNotThrow(()-> json.toJson(123));
    }


    @Test
    public void toJsonTest() throws JsonProcessingException {
        Json json = new Json();
        ObjectMapper objMapper = new ObjectMapper();
        ServerResponse response = new ServerResponse("success", Map.of("key1", "value1"), Map.of("key2", "value2"));
        String jsonString = objMapper.writeValueAsString(response);
        //expected JSON string
//        String jsonString = "{\"name\":\"HAL\",\"age\":14}";
//        String j = "{\"name\":\"HAL\",\"age\":145}";

//        String idk = objMapper.writeValueAsString()

//        String jsonString = json.toJson("HAL");
        assertEquals(jsonString, json.toJson(response));
    }

//    @Test
//    public void fromJsonTest() throws JsonProcessingException {
//        ObjectMapper objMaper = new ObjectMapper();
//        Json json = new Json();
//        ServerResponse response = new ServerResponse("success", Map.of("key1", "value1"), Map.of("key2", "value2"));
//
//        String toJsonString = objMaper.writeValueAsString(response);
//
//        ServerResponse res = objMaper.readValue(toJsonString, ServerResponse.class);
//
//        assertEquals(res, json.fromJson(toJsonString));
//        assertDoesNotThrow(()-> json.fromJson("{\"name\":\"HAL\",\"age\":14}"));
//    }

    @Test
    public void fromJsonThrowsException(){
        Json json = new Json();

        assertThrows(RuntimeException.class,
                ()->{
            json.fromJson("{\"name\":\"HAL\",\"age\":14}");
                });
    }
}
