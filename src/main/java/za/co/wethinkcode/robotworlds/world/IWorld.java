package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Robot;

import java.util.List;
import java.util.Map;

/**
 * The IWorld interface defines the behavior of a world in which robots operate.
 * It provides methods for updating robot positions, directions, and checking for obstacles.
 */
public interface IWorld {

    /**
     * Enum representing directions in the world.
     */
    enum WorldDirection {
        UP, DOWN, LEFT, RIGHT;

        /**
         * Retrieves a WorldDirection enum from its ordinal value.
         *
         * @param ordinal the ordinal value of the enum.
         * @return the corresponding WorldDirection enum.
         * @throws IllegalArgumentException if the ordinal is invalid.
         */
        public static WorldDirection fromOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal >= Direction.values().length) {
                throw new IllegalArgumentException("Invalid ordinal.");
            }
            return values()[ordinal];
        }
    }

    /**
     * Enum representing update responses for position updates.
     */
    enum UpdateResponse {
        SUCCESS, // Position was updated successfully.
        FAILED_OUTSIDE_WORLD, // Robot would go outside world limits if allowed, so it failed to update the position.
        FAILED_OBSTRUCTED, // Robot obstructed by at least one obstacle, thus cannot proceed.
    }

    /**
     * The center position of the world.
     */
    Position CENTRE = new Position(0, 0);

    /**
     * Updates the position of the robot by the specified number of steps.
     *
     * @param nrSteps the number of steps to move.
     * @return the update response indicating the outcome of the position update.
     */
    UpdateResponse updatePosition(int nrSteps);

    /**
     * Updates the direction of the robot, either turning it right or left.
     *
     * @param turnRight true to turn right, false to turn left.
     */
    void updateDirection(boolean turnRight);

    /**
     * Retrieves the current position of the robot in the world.
     *
     * @return the current position of the robot.
     */
    Position getPosition();

    /**
     * Retrieves the current direction of the robot in the world.
     *
     * @return the current direction of the robot.
     */
    Direction getCurrentDirection();

    /**
     * Checks if the given position is allowed in the world.
     *
     * @param position the position to check.
     * @return true if the position is allowed, false otherwise.
     */
    boolean isNewPositionAllowed(Position position);

    /**
     * Checks if the robot is at the edge of the world.
     *
     * @return true if the robot is at the edge, false otherwise.
     */
    boolean isAtEdge();

    /**
     * Resets the world to its initial state.
     */
    void reset();

    /**
     * Retrieves the list of obstacles in the world.
     *
     * @return the list of obstacles.
     */
    List<Obstacle> getObstacles();

    /**
     * Displays the obstacles in the world.
     */
    void showObstacles();

    /**
     * Adds a robot to the world with the specified attributes.
     *
     * @param nameAndMake   the name and make of the robot.
     * @param maximumShots  the maximum number of shots for the robot.
     * @param PORT          the port of the robot.
     * @return the added robot.
     */
    Robot addRobotToWorld(String[] nameAndMake, int maximumShots, int PORT);

    /**
     * Adds a robot to the list of robots in the world.
     *
     * @param robot the robot to add.
     * @param PORT  the port of the robot.
     */
    void addRobotToList(Robot robot, int PORT);

    /**
     * Removes a robot from the world.
     *
     * @param PORT the port of the robot to remove.
     */
    void removeRobot(int PORT);

    /**
     * Retrieves the map of robots in the world.
     *
     * @return the map of robots.
     */
    Map<Integer, Robot> getRobots();

    /**
     * Validates the launch position of a robot.
     *
     * @param position the position to validate.
     * @return the validated launch position.
     */
    Position validateLaunchPosition(Position position);

    /**
     * Displays the current state of the world.
     */
    void showWorldState();

    /**
     * Displays the robots in the world.
     */
    void showRobots();

    /**
     * Retrieves the visibility parameter of the world.
     *
     * @return the visibility parameter.
     */
    int getVisibility();

    /**
     * Retrieves the reload time parameter of the world.
     *
     * @return the reload time parameter.
     */
    int getReload();

    /**
     * Retrieves the repair time parameter of the world.
     *
     * @return the repair time parameter.
     */
    int getRepair();

    /**
     * Retrieves the shields parameter of the world.
     *
     * @return the shield's parameter.
     */
    int getShields();

    /**
     * Retrieves the edges of the world.
     *
     * @return the edges of the world.
     */
    Edge getWorldEdges();
}
