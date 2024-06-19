package za.co.wethinkcode.robotworlds.world.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.ConfigUtility;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @BeforeEach
    void setUp() {
        ConfigUtility config = ConfigUtility.readConfiguration();
    }

    @Test
    void testGetVisibility() {
        assertEquals(20, ConfigUtility.getVisibility(), "Visibility should be initialized correctly from config.json");
    }

}
