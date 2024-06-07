package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import za.co.wethinkcode.robotworlds.client.ClientRequest;
import za.co.wethinkcode.robotworlds.command.Command;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

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
            throw new RuntimeException("Json.toJson exception: "+e);
        }
    }

    public ServerResponse fromJson(String json) {
        try {
            return objectMapper.readValue(json, ServerResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.fromJson exception: "+e);
        }
    }

    public JsonNode jsonFieldAccess(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.jsonFieldAccess exception: "+e);
        }
    }
}
