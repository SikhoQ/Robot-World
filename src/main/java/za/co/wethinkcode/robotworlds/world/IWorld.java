package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import java.util.*;

public interface IWorld {

    enum WorldDirection {
        UP, DOWN, LEFT, RIGHT;

        public static WorldDirection fromOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal >= Direction.values().length) {
                throw new IllegalArgumentException("Invalid ordinal.");
            }
            return values()[ordinal];
        }
    }

    enum UpdateResponse {
        SUCCESS, //position was updated successfully
        FAILED_OUTSIDE_WORLD, //robot will go outside world limits if allowed, so it failed to update the position
        FAILED_OBSTRUCTED, //robot obstructed by at least one obstacle, thus cannot proceed.
    }

    Position CENTRE = new Position(0,0);

    UpdateResponse updatePosition(int nrSteps);

    void updateDirection(boolean turnRight);

    Position getPosition();

    Direction getCurrentDirection();

    boolean isNewPositionAllowed(Position position);

    boolean isAtEdge();

    void reset();

    List<Obstacle> getObstacles();

    void showObstacles();

    SimpleBot launchRobot(String make, String name, int maximumShots, int PORT);
    void removeRobot(int PORT);
    Map<Integer, SimpleBot> getRobots();
    Position validateLaunchPosition(Position position);

    void showWorldState();

    void showRobots();

    int getVisibility();

    int getReload();

    int getRepair();

    int getShields();

    Edge getWorldEdges();
}
