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

}
