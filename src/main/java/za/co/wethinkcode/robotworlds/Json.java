package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import java.util.*;

public class Json {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.toJson exception: "+e);
        }
    }

    public static ServerResponse fromJson(String json) {
        try {
            return objectMapper.readValue(json, ServerResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.fromJson exception: "+e);
        }
    }

    public static Map<String, Object> getJsonFields(JsonNode rootNode) {
        Map<String, Object> jsonFields = new HashMap<>();
        String name = rootNode.get("robot").asText();
        String command = rootNode.get("command").asText();
        JsonNode argumentsNode = rootNode.get("arguments");
        Object[] arguments = new Object[] {};

        if (argumentsNode != null && argumentsNode.isArray()) {
            List<Object> argumentsList = new ArrayList<>();
            Iterator<JsonNode> elements = argumentsNode.elements();
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                if (element.isTextual()) {
                    argumentsList.add(element.asText());
                } else if (element.isInt()) {
                    argumentsList.add(element.asInt());
                }
            }
            arguments = argumentsList.toArray();
        } else {
            System.out.println("No arguments found or arguments is not an array.");
        }
        jsonFields.put("robot", name);
        jsonFields.put("command", command);
        jsonFields.put("arguments", arguments);

        return jsonFields;
    }

    public static JsonNode jsonFieldAccess(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.jsonFieldAccess exception: "+e);
        }
    }
}
