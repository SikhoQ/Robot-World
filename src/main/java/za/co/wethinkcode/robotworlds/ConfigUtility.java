package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robotworlds.server.configuration.ServerConfiguration;
import za.co.wethinkcode.robotworlds.server.configuration.WorldSize;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * The ConfigUtility class provides utility methods to manage server configuration.
 */
public class ConfigUtility {
    private static WorldSize worldSize;
    private static int visibility;
    private static int reload;
    private static int repair;
    private static int shields;
    private static int port;

    /**
     * Sets the port for the server.
     * @param port The port number to set.
     */
    public static void setPort(int port) {
        ConfigUtility.port = port;
    }

    /**
     * Gets the port used by the server.
     * @return The port number.
     */
    public static int getPort() {
        return ConfigUtility.port;
    }

    /**
     * Gets the visibility setting.
     * @return The visibility setting.
     */
    public static int getVisibility() {
        return visibility==0?20:ConfigUtility.visibility;
    }

    /**
     * Gets the reload setting.
     * @return The reload setting.
     */
    public static int getReload() {
        return reload;
    }

    /**
     * Sets the reload setting.
     * @param reload The reload value to set.
     */
    public void setReload(int reload) {
        ConfigUtility.reload = reload;
    }

    /**
     * Gets the repair setting.
     * @return The repair setting.
     */
    public static int getRepair() {
        return repair;
    }

    /**
     * Sets the repair setting.
     * @param repair The repair value to set.
     */
    public void setRepair(int repair) {
        ConfigUtility.repair = repair;
    }

    /**
     * Gets the shields setting.
     * @return The shields setting.
     */
    public static int getShields() {
        return shields;
    }

    /**
     * Sets the shields setting.
     * @param shields The shields value to set.
     */
    public void setShields(int shields) {
        ConfigUtility.shields = shields;
    }

    public void setWorldSize(WorldSize worldSize) {
        ConfigUtility.worldSize = worldSize;
    }

    public void setVisibility(int visibility) {
        ConfigUtility.visibility = visibility;
    }

    /**
     * Gets the world size configuration.
     * @return The world size configuration.
     */
    public static WorldSize getWorldSize() {
        return new WorldSize();
    }

    /**
     * Reads the server configuration from a JSON file.
     * @return The ConfigUtility instance.
     */
    public static ConfigUtility readConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = ConfigUtility.class.getClassLoader().getResourceAsStream("serverConfig.json")) {
            if (inputStream == null) {
                ServerConfiguration.configureServer();
            }
            return mapper.readValue(inputStream, ConfigUtility.class);
        } catch (IOException e) {
            throw new RuntimeException("Error reading configuration file: " + e.getMessage(), e);
        }
    }

    /**
     * Loads the server configuration from a JSON file.
     * @return The ObjectNode containing the configuration.
     */
    public static ObjectNode loadConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile = new File(ServerConfiguration.SERVER_CONFIG_FILE_PATH);
        ObjectNode config = objectMapper.createObjectNode();
        try {
            if (configFile.createNewFile()) {
                System.out.println("Created empty configuration file.");
            } else {
                config = (ObjectNode) objectMapper.readTree(configFile);
            }
        } catch (IOException e) {
            System.out.println("Failed to load configuration file. Starting with empty configuration.");
        }
        return config;
    }
}
