package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * The FireCommand class is responsible for executing the fire action for a robot.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class FireCommand extends Command {
    private SimpleBot enemyRobot;
    protected int enemyRobotDistance = 0;
    /**
     * Constructor for FireCommand.
     */
    public FireCommand() {
        super("fire", null);
    }

    /**
     * Executes the fire command on the given target robot in the specified world.
     *
     * @param target the SimpleBot that will execute the command.
     * @param world the IWorld in which the robot operates.
     * @return a ServerResponse containing the result, data, and state after executing the command.
     */

    @Override
    public ServerResponse execute(SimpleBot target, IWorld world) {
        // Robot fires a shot
        target.getGun().fireShot();

        String result = "Ok";
        Position position = target.getPosition();
        Direction direction = target.getDirection();

        int shotDistance = target.getGun().getShotDistance();
        int robotY = position.getY();
        int robotX = position.getX();
        String message = "";

        // Determine the bullet trajectory based on the direction
        switch (direction) {
            case NORTH:
                for (int y = robotY + 1; y <= (robotY + shotDistance); y++) {
                    if ((message = reportHit(world, position, y, Direction.NORTH)).equals("HIT")) {
                        break;
                    }
                }
                break;
            case SOUTH:
                for (int y = robotY - 1; y >= (robotY - shotDistance); y--) {
                    if ((message = reportHit(world, position, y, Direction.SOUTH)).equals("HIT")) {
                        break;
                    }
                }
                break;
            case EAST:
                for (int x = robotX + 1; x <= (robotX + shotDistance); x++) {
                    if ((message = reportHit(world, position, x, Direction.EAST)).equals("HIT")) {
                        break;
                    }
                }
                break;
            case WEST:
                for (int x = robotX - 1; x >= (robotX - shotDistance); x--) {
                    if ((message = reportHit(world, position, x, Direction.WEST)).equals("HIT")) {
                        break;
                    }
                }
                break;
        }

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> state = new HashMap<>();

        if (message.equals("MISS")) {
            data.put("message", "Miss");
        } else {
            enemyRobot.updateShields();
            if (enemyRobot.getShields() == 0) {
                world.removeRobot(enemyRobot.getPORT());
            }

            data.put("message", "Hit");
            data.put("distance", enemyRobotDistance);
            data.put("name", enemyRobot.getName());
            Map<String, Object> robotShotState = new HashMap<>();
            robotShotState.put("position", enemyRobot.getPosition());
            robotShotState.put("direction", enemyRobot.getDirection());
            robotShotState.put("shields", enemyRobot.getShields());
            robotShotState.put("shots", enemyRobot.getGun().getNumberOfShots());
            robotShotState.put("status", enemyRobot.getStatus());

            data.put("state", robotShotState);
        }

        state.put("shots", target.getGun().getNumberOfShots());

        return new ServerResponse(result, data, state);
    }

    /**
     * Checks if the bullet hits any obstacles, world edges, or other robots.
     *
     * @param world the IWorld in which the robot operates.
     * @param robotPosition the position of the robot firing the shot.
     * @param bulletPosition the current position of the bullet.
     * @param direction the direction in which the bullet is traveling.
     * @return "HIT" if the bullet hits a robot, "MISS" otherwise.
     */

    public String reportHit(IWorld world, Position robotPosition, int bulletPosition, Direction direction) {
        Map<Integer, SimpleBot> worldRobots = world.getRobots();
        List<SimpleBot> robots = new ArrayList<>(worldRobots.values());
        List<Obstacle> obstacles = world.getObstacles();
        int robotX = robotPosition.getX();
        int robotY = robotPosition.getY();

        for (Obstacle obstacle : obstacles) {
            if ((direction == Direction.WEST && bulletPosition == (obstacle.getBottomLeftX() + 4) &&
                    (robotY >= obstacle.getBottomLeftY() && robotY <= (obstacle.getBottomLeftY() + 4))) ||
                    (direction == Direction.EAST && bulletPosition == obstacle.getBottomLeftX() &&
                            (robotY >= obstacle.getBottomLeftY() && robotY <= (obstacle.getBottomLeftY() + 4))) ||
                    (direction == Direction.SOUTH && bulletPosition == (obstacle.getBottomLeftY() + 4) &&
                            (robotX >= obstacle.getBottomLeftX() && robotX <= (obstacle.getBottomLeftX() + 4))) ||
                    (direction == Direction.NORTH && bulletPosition == obstacle.getBottomLeftY() &&
                            (robotX >= obstacle.getBottomLeftX() && robotX <= (obstacle.getBottomLeftX() + 4)))) {
                return "MISS";
            }
        }

        if ((direction == Direction.WEST && bulletPosition == world.getWorldEdges().getMinimumX()) ||
                (direction == Direction.EAST && bulletPosition == world.getWorldEdges().getMaximumX()) ||
                (direction == Direction.SOUTH && bulletPosition == world.getWorldEdges().getMinimumY()) ||
                (direction == Direction.NORTH && bulletPosition == world.getWorldEdges().getMaximumY())) {
            return "MISS";
        }

        for (SimpleBot robot : robots) {
            if ((bulletPosition == robot.getPosition().getX() && robotY == robot.getPosition().getY()) ||
                    (bulletPosition == robot.getPosition().getY() && robotX == robot.getPosition().getX())) {

                setEnemyRobot(robot);
                enemyRobotDistance = switch (direction) {
                    case NORTH -> robot.getPosition().getY() - robotY;
                    case SOUTH -> robotY - robot.getPosition().getY();
                    case EAST -> robot.getPosition().getX() - robotX;
                    default -> robotX - robot.getPosition().getX();
                };

                return "HIT";
            }
        }
        return "MISS";
    }

    /**
     * Get the enemy robot that was hit.
     *
     * @return the enemy robot that was hit.
     */
    public SimpleBot getEnemyRobot() {
        return enemyRobot;
    }


    /**
     * Set the enemy robot that was hit.
     *
     * @param enemyRobot the enemy robot that was hit.
     */

    public void setEnemyRobot(SimpleBot enemyRobot) {
        this.enemyRobot = enemyRobot;
    }
}
