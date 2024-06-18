package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.util.*;

/**
 * Utility class for JSON operations.
 */
public class JsonUtility {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts an object to a JSON string.
     * @param object The object to convert.
     * @return The JSON representation of the object.
     * @throws RuntimeException if an error occurs during JSON serialization.
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.toJson exception: " + e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON string to a ServerResponse object.
     * @param json The JSON string to convert.
     * @return The ServerResponse object parsed from the JSON string.
     */
    public static ServerResponse fromJson(String json) {
        try {
            return objectMapper.readValue(json, ServerResponse.class);
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return new ServerResponse("error", null, null);
        }
    }

    /**
     * Extracts fields from a JSON node.
     * @param rootNode The root JSON node.
     * @return An optional containing the extracted fields as a map.
     * @throws IllegalArgumentException if the JSON format is invalid.
     */
    public static Optional<Map<String, Object>> getJsonFields(JsonNode rootNode) throws IllegalArgumentException {
        String name = rootNode.get("robot").asText();
        String command = rootNode.get("command").asText();
        JsonNode argumentsNode = rootNode.get("arguments");
        Object[] arguments = {};

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

    /**
     * Accesses fields of a JSON string.
     * @param jsonString The JSON string to access.
     * @return The root JSON node.
     * @throws RuntimeException if an error occurs during JSON parsing.
     */
    public static JsonNode jsonFieldAccess(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.jsonFieldAccess exception: " + e.getMessage(), e);
        }
    }
}
