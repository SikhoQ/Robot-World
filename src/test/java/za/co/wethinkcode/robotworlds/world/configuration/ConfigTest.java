package za.co.wethinkcode.robotworlds.world.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.configuration.Config;
import za.co.wethinkcode.robotworlds.world.configuration.WorldSize;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    private Config config;

    @BeforeEach
    void setUp() {
        config = Config.readConfiguration();
    }

    @Test
    void testGetVisibility() {
        assertEquals(50, config.getVisibility(), "Visibility should be initialized correctly from config.json");
    }
//
//    @Test
//    void testGetReload() {
//        assertEquals(5, config.getReload(), "Reload should be initialized correctly from config.json");
//    }
//
//    @Test
//    void testGetRepair() {
//        assertEquals(5, config.getRepair(), "Repair should be initialized correctly from config.json");
//    }
//
//    @Test
//    void testGetShields() {
//        assertEquals(5, config.getShields(), "Shields should be initialized correctly from config.json");
//    }
//
//    @Test
//    void testGetWorldSize() {
//        WorldSize worldSize = config.getWorldSize();
//        assertNotNull(worldSize, "WorldSize object should not be null");
//        assertEquals(200, worldSize.getWidth(), "Width should be initialized correctly from config.json");
//        assertEquals(400, worldSize.getHeight(), "Height should be initialized correctly from config.json");
//    }
//
//    @Test
//    void testReadConfiguration() {
//        Config config = Config.readConfiguration();
//        assertNotNull(config, "Config object should not be null");
//        assertEquals(50, config.getVisibility(), "Visibility should be initialized correctly from config.json");
//        assertEquals(5, config.getReload(), "Reload should be initialized correctly from config.json");
//        assertEquals(5, config.getRepair(), "Repair should be initialized correctly from config.json");
//        assertEquals(5, config.getShields(), "Shields should be initialized correctly from config.json");
//        WorldSize worldSize = config.getWorldSize();
//        assertNotNull(worldSize, "WorldSize object should not be null");
//        assertEquals(200, worldSize.getWidth(), "Width should be initialized correctly from config.json");
//        assertEquals(400, worldSize.getHeight(), "Height should be initialized correctly from config.json");
//    }
}
