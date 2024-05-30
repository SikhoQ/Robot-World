package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import za.co.wethinkcode.robotworlds.client.ClientRequest;
import za.co.wethinkcode.robotworlds.command.Command;

import java.io.IOException;

public class Json {
    private final ObjectMapper objectMapper;

    public Json() {
        objectMapper = new ObjectMapper();
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "toJson exception";
        }
    }

    public Command fromJson(String json) throws IOException {
        return objectMapper.readValue(json, Command.class);
    }

    public JsonNode jsonFieldAccess(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
