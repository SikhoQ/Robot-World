package za.co.wethinkcode.robotworlds.world;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
    @Test
    public void gettingRandomDirection(){
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.NORTH);
        directions.add(Direction.EAST);
        directions.add(Direction.SOUTH);
        directions.add(Direction.WEST);

        assertTrue(directions.contains(Direction.getRandomDirection()));
    }

    @Test
    public void fromOrdinalExceptionGreaterThanFour(){
        assertThrows(IllegalArgumentException.class,
                ()->{
            Direction.fromOrdinal(5);
                });
    }

    @Test
    public void fromOrdinalExceptionLessThanOne(){
        assertThrows(IllegalArgumentException.class,
                ()->{
            Direction.fromOrdinal(-1);
                });
    }
}
