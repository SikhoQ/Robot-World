package za.co.wethinkcode.robotworlds.maze;

import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.Obstacle;

import java.util.List;

public interface Maze {
    List<Obstacle> getObstacles();

    boolean blocksPath(Position a, Position b);
}

