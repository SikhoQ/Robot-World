package za.co.wethinkcode.robotworlds.world.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigUtility {
    private WorldSize worldSize;
    private int visibility;
    private int reload;
    private int repair;
    private int shields;
    private int port;
    private String serverName;

    public String getServerName() {
        return serverName;
    }

    public int getPort() {
        return port;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getReload() {
        return reload;
    }

    public void setReload(int reload) {
        this.reload = reload;
    }

    public int getRepair() {
        return repair;
    }

    public void setRepair(int repair) {
        this.repair = repair;
    }

    public int getShields() {
        return shields;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public WorldSize getWorldSize() {
        return worldSize;
    }

    public void setWorldSize(WorldSize worldSize) {
        this.worldSize = worldSize;
    }

    public static ConfigUtility readConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = ConfigUtility.class.getClassLoader().getResourceAsStream("serverConfig.json")) {
            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found in resources");
            }
            return mapper.readValue(inputStream, ConfigUtility.class);
        } catch (IOException e) {
            throw new RuntimeException("Error reading configuration file: " + e.getMessage(), e);
        }
    }
}
