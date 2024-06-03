package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import za.co.wethinkcode.robotworlds.client.ClientRequest;
import za.co.wethinkcode.robotworlds.command.Command;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.io.IOException;

/**
 * The Json class provides methods for converting objects to and from JSON format.
 */
public class Json {
    private final ObjectMapper objectMapper;

    /**
     * Constructs a Json object with a default ObjectMapper.
     */
    public Json() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Converts an object to its JSON representation.
     *
     * @param object The object to be converted.
     * @return The JSON representation of the object.
     * @throws RuntimeException if an error occurs during JSON serialization.
     */
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.toJson exception: " + e);
        }
    }

    /**
     * Converts a JSON string to a ServerResponse object.
     *
     * @param json The JSON string to be converted.
     * @return The ServerResponse object parsed from the JSON string.
     * @throws RuntimeException if an error occurs during JSON deserialization.
     */
    public ServerResponse fromJson(String json) {
        try {
            return objectMapper.readValue(json, ServerResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.fromJson exception: " + e);
        }
    }

    /**
     * Extracts a specific field from a JSON string as a JsonNode.
     *
     * @param jsonString The JSON string from which to extract the field.
     * @return The JsonNode representing the extracted field.
     * @throws RuntimeException if an error occurs during JSON parsing.
     */
    public JsonNode jsonFieldAccess(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json.jsonFieldAccess exception: " + e);
        }
    }
}
