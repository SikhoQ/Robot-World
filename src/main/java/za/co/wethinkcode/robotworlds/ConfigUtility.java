package za.co.wethinkcode.robotworlds;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robotworlds.server.configuration.ServerConfiguration;
import za.co.wethinkcode.robotworlds.server.configuration.WorldSize;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ConfigUtility {
    private static WorldSize worldSize;
    private static int visibility;
    private static int reload;
    private static int repair;
    private static int shields;
    private static int port;
    private String serverName;

    public static void setPort(int port) {
        ConfigUtility.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public static int getPort() {
        return ConfigUtility.port;
    }

    public static int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        ConfigUtility.visibility = visibility;
    }

    public static int getReload() {
        return reload;
    }

    public void setReload(int reload) {
        ConfigUtility.reload = reload;
    }

    public static int getRepair() {
        return repair;
    }

    public void setRepair(int repair) {
        ConfigUtility.repair = repair;
    }

    public static int getShields() {
        return shields;
    }

    public void setShields(int shields) {
        ConfigUtility.shields = shields;
    }

    public static WorldSize getWorldSize() {
        return new WorldSize();
    }

    public void setWorldSize(WorldSize worldSize) {
        ConfigUtility.worldSize = worldSize;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public static ConfigUtility readConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = ConfigUtility.class.getClassLoader().getResourceAsStream("serverConfig.json")) {
            if (inputStream == null) {
                ServerConfiguration.configureServer();
//                throw new RuntimeException("Configuration file not found in resources");
            }
            return mapper.readValue(inputStream, ConfigUtility.class);
        } catch (IOException e) {
            throw new RuntimeException("Error reading configuration file: " + e.getMessage(), e);
        }
    }

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
