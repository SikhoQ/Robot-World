package za.co.wethinkcode.robotworlds.maze;

import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;
import za.co.wethinkcode.robotworlds.Position;
<<<<<<< HEAD
import za.co.wethinkcode.robotworlds.world.configuration.Config;
=======
import za.co.wethinkcode.robotworlds.ConfigUtility;

>>>>>>> sikho
import java.util.*;

/**
 * Class to represent a simple maze implementation containing obstacles.
 */
public class RandomMaze implements Maze {

    List<Obstacle> obstacles;

    public RandomMaze() {
        this.obstacles = new ArrayList<>();
    }

    /**
     * Returns the list of obstacles in the maze.
     *
     * @return a list of Obstacle objects representing the obstacles in the maze.
     */
    @Override
    public List<Obstacle> getObstacles() {
        createObstacles();
        return obstacles;
    }

    /**
     * Checks if the path from the start position to the destination position is blocked by any obstacle.
     *
     * @param start the starting position of the path
     * @param dest the destination position of the path
     * @return true if the path is blocked by an obstacle, false otherwise
     */
    @Override
    public boolean blocksPath(Position start, Position dest) {

        Obstacle[] obstaclesArray = obstacles.toArray(new Obstacle[obstacles.size() - 1]);
        for (Obstacle obs: obstaclesArray) {
            // vertical path
            if (start.getX() == dest.getX()) {
                if ((obs.getBottomLeftY() >= Math.min(start.getY(), dest.getY())
                        && obs.getBottomLeftY() <= Math.max(start.getY(), dest.getY()))
                        && (start.getX() >= obs.getBottomLeftX() && start.getX() <= obs.getBottomLeftX() + 4)) {
                    return true;
                }
            }
            // horizontal
            else {
                if ((obs.getBottomLeftX() >= Math.min(start.getX(), dest.getX())
                        && obs.getBottomLeftX() <= Math.max(start.getX(), dest.getX()))
                        && (start.getY() >= obs.getBottomLeftY() && start.getY() <= obs.getBottomLeftY() + 4)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void createObstacles() {
        Random random = new Random();

<<<<<<< HEAD
        int worldSize = config.getWorldSize().getHeight() * config.getWorldSize().getWidth();
        int minimumObstacles = 1;
        int maximumObstacles = 3;

=======
        int worldSize = ConfigUtility.getWorldSize().getHeight() * ConfigUtility.getWorldSize().getWidth();
        int minimumObstacles = (int) (worldSize * 0.001);
        int maximumObstacles = (int) (worldSize * 0.002);
>>>>>>> sikho
        int numberOfObstacles = random.nextInt((maximumObstacles - minimumObstacles) + 1) + minimumObstacles;
        int worldX = ConfigUtility.getWorldSize().getWidth() / 2;
        int worldY = ConfigUtility.getWorldSize().getHeight() / 2;

        for (int i = 0; i < numberOfObstacles; i++) {
            int xCoord = random.nextInt((worldX + worldX) + 1) - worldX;
            int yCoord = random.nextInt((worldY + worldY) + 1) - worldY;

            for (Obstacle obstacle: obstacles) {
                while (obstacle.blocksPosition(new Position(xCoord, yCoord))) {
                    xCoord = random.nextInt((worldX + worldX) + 1) - worldX;
                    yCoord = random.nextInt((worldY + worldY) + 1) - worldY;
                }
            }
            obstacles.add(new SquareObstacle(xCoord, yCoord));
        }
    }
}

