package za.co.wethinkcode.robotworlds.maze;

import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.ConfigUtility;

import java.util.*;

/**
 * The RandomMaze class represents a randomly generated maze with obstacles.
 * It implements the Maze interface and provides methods to retrieve obstacles and check path blockages.
 */
public class RandomMaze implements Maze {

    /** List to store obstacles in the maze. */
    private final List<Obstacle> obstacles;

    /**
     * Constructs a new RandomMaze object with an empty list of obstacles.
     */
    public RandomMaze() {
        this.obstacles = new ArrayList<>();
    }

    /**
     * Retrieves a list of obstacles in the maze.
     *
     * @return a list of obstacles present in the maze.
     */
    @Override
    public List<Obstacle> getObstacles() {
        createObstacles();
        return obstacles;
    }

    /**
     * Checks if a path between two positions in the maze is blocked by obstacles.
     *
     * <p>This method determines whether there are any obstacles blocking the path between
     * the specified positions {@code start} and {@code dest} in the maze.</p>
     *
     * @param start the starting position of the path.
     * @param dest the ending position of the path.
     * @return {@code true} if the path between positions {@code start} and {@code dest} is blocked by obstacles,
     *         {@code false} otherwise.
     */
    @Override
    public boolean blocksPath(Position start, Position dest) {
        Obstacle[] obstaclesArray = obstacles.toArray(new Obstacle[0]);
        for (Obstacle obs : obstaclesArray) {
            if (start.getX() == dest.getX()) { // Vertical path
                if ((obs.getBottomLeftY() >= Math.min(start.getY(), dest.getY())
                        && obs.getBottomLeftY() <= Math.max(start.getY(), dest.getY()))
                        && (start.getX() >= obs.getBottomLeftX() && start.getX() <= obs.getBottomLeftX() + 4)) {
                    return true;
                }
            } else { // Horizontal path
                if ((obs.getBottomLeftX() >= Math.min(start.getX(), dest.getX())
                        && obs.getBottomLeftX() <= Math.max(start.getX(), dest.getX()))
                        && (start.getY() >= obs.getBottomLeftY() && start.getY() <= obs.getBottomLeftY() + 4)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Generates and adds obstacles to the maze based on configuration settings.
     */
    private void createObstacles() {
        Random random = new Random();

        int worldSize = ConfigUtility.getWorldSize().getHeight() * ConfigUtility.getWorldSize().getWidth();
        int minimumObstacles = (int) (worldSize * 0.01);
        int maximumObstacles = (int) (worldSize * 0.02);
        int numberOfObstacles = random.nextInt((maximumObstacles - minimumObstacles) + 1) + minimumObstacles;
        int worldX = ConfigUtility.getWorldSize().getWidth() / 2;
        int worldY = ConfigUtility.getWorldSize().getHeight() / 2;

        for (int i = 0; i < numberOfObstacles; i++) {
            int xCoord = random.nextInt((worldX + worldX) + 1) - worldX;
            int yCoord = random.nextInt((worldY + worldY) + 1) - worldY;

            for (Obstacle obstacle : obstacles) {
                while (obstacle.blocksPosition(new Position(xCoord, yCoord))) {
                    xCoord = random.nextInt((worldX + worldX) + 1) - worldX;
                    yCoord = random.nextInt((worldY + worldY) + 1) - worldY;
                }
            }
            obstacles.add(new SquareObstacle(xCoord, yCoord));
        }
    }
}
