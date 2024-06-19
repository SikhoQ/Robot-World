package za.co.wethinkcode.robotworlds.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.SniperBot;

import static org.junit.jupiter.api.Assertions.*;

class SniperBotTest {

    private SniperBot sniperBot;

    @BeforeEach
    void setUp() {
        // Initialize SniperBot with mock values
        Position position = new Position(0, 0);
        Direction direction = Direction.NORTH;
        sniperBot = new SniperBot("TestBot", position, direction, 1);
    }

    @Test
    void testInitialShields() {
        assertEquals(1, sniperBot.getShields(), "SniperBot should have shields initialized to 1");
    }

    @Test
    void testGetPosition() {
        Position position = sniperBot.getPosition();
        assertNotNull(position, "Position should not be null");
        assertEquals(0, position.getX(), "Initial X position should be 0");
        assertEquals(0, position.getY(), "Initial Y position should be 0");
    }

    @Test
    void testGetDirection() {
        Direction direction = sniperBot.getDirection();
        assertNotNull(direction, "Direction should not be null");
        assertEquals(Direction.NORTH, direction, "Initial direction should be NORTH");
    }

    // Add more tests specific to SniperBot's behavior if needed
}
