package za.co.wethinkcode.robotworlds.maze;

import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;
import za.co.wethinkcode.robotworlds.Position;
import java.util.*;


public class SimpleMaze implements Maze {

    List<Obstacle> obstacles;

    public SimpleMaze() {
        this.obstacles = new ArrayList<>();
    }

    @Override
    public List<Obstacle> getObstacles() {
        createObstacles();
        return obstacles;
    }

    @Override
    public boolean blocksPath(Position start, Position destination) {

        Obstacle[] obstaclesArray = obstacles.toArray(new Obstacle[obstacles.size() - 1]);
        for (Obstacle obs: obstaclesArray) {
            if (start.getX() == destination.getX()) {
                if ((obs.getBottomLeftY() >= Math.min(start.getY(), destination.getY())
                        && obs.getBottomLeftY() <= Math.max(start.getY(), destination.getY()))
                        && (start.getX() >= obs.getBottomLeftX() && start.getX() <= obs.getBottomLeftX() + 4)) {
                    return true;
                }
            }

            else {
                if ((obs.getBottomLeftX() >= Math.min(start.getX(), destination.getX())
                        && obs.getBottomLeftX() <= Math.max(start.getX(), destination.getX()))
                        && (start.getY() >= obs.getBottomLeftY() && start.getY() <= obs.getBottomLeftY() + 4)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void createObstacles() {
        SquareObstacle obstacle = new SquareObstacle(1, 1);
        obstacles.add(obstacle);
    }
}

