package za.co.wethinkcode.robotworlds.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.TextWorld;

public class SimpleBotTest {
    @Mock
    IWorld world;
    SimpleBot simpleBot;
    Position position;

//    IWorld world = new TextWorld();
//    SimpleBot simpleBot = new SimpleBot("John", new Position(10, 20), Direction.NORTH,3389);

    @BeforeEach
    void setUp() {
        world = mock(IWorld.class);
        simpleBot = mock(SimpleBot.class);
    }

    @Test
    public void updatingPosition(){
        when(simpleBot.updatePosition(10, world)).thenReturn("Done");
        assertEquals("Done", simpleBot.updatePosition(10, world));
    }

    @Test
    public void isPositionBlocked(){
//        SimpleBot simpleBot = new SimpleBot("John", new Position(10, 20), Direction.NORTH,3389);
//        Position position = new Position(10, 20);
        when(simpleBot.blocksPosition(position)).thenReturn(true);
        assertTrue(simpleBot.blocksPosition(position));
    }
}
