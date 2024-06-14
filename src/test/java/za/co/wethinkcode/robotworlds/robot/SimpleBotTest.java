package za.co.wethinkcode.robotworlds.robot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.TextWorld;

public class SimpleBotTest {
    IWorld world = new TextWorld();
    SimpleBot simpleBot = new SimpleBot("John", new Position(10, 20), Direction.NORTH,3389);

    @Test
    public void updatingPosition(){
        assertEquals("Done", simpleBot.updatePosition(10, world));
    }

    @Test
    public void isPositionBlocked(){
        SimpleBot simpleBot = new SimpleBot("John", new Position(10, 20), Direction.NORTH,3389);
        Position position = new Position(10, 20);
        assertTrue(simpleBot.blocksPosition(position));
    }
}
