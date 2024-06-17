package za.co.wethinkcode.robotworlds.world.configuration;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.configuration.WorldSize;

import static org.junit.jupiter.api.Assertions.*;

class WorldSizeTest {

    @Test
    void testGetWidth() {
        WorldSize worldSize = new WorldSize();
        worldSize.setWidth(100);
        assertEquals(100, worldSize.getWidth(), "Width should be 100");
    }

    @Test
    void testSetWidth() {
        WorldSize worldSize = new WorldSize();
        worldSize.setWidth(200);
        assertEquals(200, worldSize.getWidth(), "Width should be set correctly to 200");
    }

    @Test
    void testGetHeight() {
        WorldSize worldSize = new WorldSize();
        worldSize.setHeight(50);
        assertEquals(50, worldSize.getHeight(), "Height should be 50");
    }

    @Test
    void testSetHeight() {
        WorldSize worldSize = new WorldSize();
        worldSize.setHeight(150);
        assertEquals(150, worldSize.getHeight(), "Height should be set correctly to 150");
    }
}
