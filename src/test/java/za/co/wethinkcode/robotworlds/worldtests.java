package za.co.wethinkcode.robotworlds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class worldtests {
    @Test
    public void shouldKnowXandY() {
        Position p = new Position(10, 20);
        assertEquals(10, p.getX());
        assertEquals(20, p.getY());

    }

}
