package za.co.wethinkcode.robotworlds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    public void gettingXandY() {
        Position position = new Position(10, 20);
        assertEquals(10, position.getX());
        assertEquals(20, position.getY());
    }

    @Test
    public void checkingEquality() {
        assertEquals(new Position(-44, 63), new Position(-44, 63));
        assertNotEquals(new Position(-44, 63), new Position(0, 63));
        assertNotEquals(new Position(-44, 63), new Position(-44, 0));
        assertNotEquals(new Position(-44, 63), new Position(0, 0));
    }

}