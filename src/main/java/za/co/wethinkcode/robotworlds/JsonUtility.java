package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
<<<<<<< HEAD:src/main/java/za/co/wethinkcode/robotworlds/Json.java
import java.util.*;
=======

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Iterator;
>>>>>>> sikho:src/main/java/za/co/wethinkcode/robotworlds/JsonUtility.java

public class JsonUtility {
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
            System.err.println("Error processing JSON: " + e.getMessage());
            return new ServerResponse("error", null, null);
        }
    }

    public static Optional<Map<String, Object>> getJsonFields(JsonNode rootNode) throws IllegalArgumentException {String name = rootNode.get("robot").asText(), command = rootNode.get("command").asText();JsonNode argumentsNode = rootNode.get("arguments");Object[] arguments = new Object[]{};

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
        } else if (argumentsNode != null && !argumentsNode.isNull()) {
            throw new IllegalArgumentException("Invalid arguments format in JSON");
        }

        Map<String, Object> jsonFields = new HashMap<>();
        jsonFields.put("robot", name);
        jsonFields.put("command", command);
        jsonFields.put("arguments", arguments);

        return Optional.of(jsonFields);
    }

    public static JsonNode jsonFieldAccess(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.jsonFieldAccess exception: "+e);
        }
    }
}
