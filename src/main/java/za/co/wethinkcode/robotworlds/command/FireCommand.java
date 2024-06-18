package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a command to fire a shot in the game.
 * It extends the Command class and overrides the execute method.
 */
public class FireCommand extends Command {
    private Robot enemyRobot;
    protected int enemyRobotDistance = 0;

    public FireCommand() {
        super("fire", null);
    }

    /**
     * Executes the fire command for the given robot in the specified world.
     * It checks if there are any shots left in the gun. If not, it returns a response indicating that the gun is empty.
     * If there are shots left, it simulates the firing of a shot in the specified direction.
     * It checks for hits, misses, and collisions with obstacles and other robots.
     * It updates the state of the robot and the world after the shot is fired.
     *
     * @param target The robot executing the command.
     * @param world The world in which the robot is executing the command.
     * @return The response from the server after executing the command.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        if (Gun.getNumberOfShots() != 0) {
            target.getGun().fireShot();
        }
        else {
            String result = "OK";
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Empty");
            Map<String, Object> state = new HashMap<>();
            state.put("position", target.getPosition());
            state.put("direction", target.getDirection());
            state.put("shields", target.getShields());
            state.put("shots", Gun.getNumberOfShots());
            state.put("status", target.getStatus());

            return new ServerResponse(result, data, state);
        }


        String result = "OK";
        Position position = target.getPosition();
        Direction direction = target.getDirection();

        int shotDistance = target.getGun().getShotDistance();
        int robotY = position.getY();
        int robotX = position.getX();
        String message = "";

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
            robotShotState.put("shots", Gun.getNumberOfShots());
            robotShotState.put("status", enemyRobot.getStatus());

            data.put("state", robotShotState);
        }

        state.put("shots", Gun.getNumberOfShots());

        return new ServerResponse(result, data, state);
    }

    /**
     * Reports a hit or miss for a bullet at a specific position in the specified direction.
     * It checks for collisions with obstacles and other robots.
     * It updates the enemyRobot and enemyRobotDistance if a hit is detected.
     *
     * @param world The world in which the bullet is moving.
     * @param robotPosition The position of the robot firing the bullet.
     * @param bulletPosition The position of the bullet.
     * @param direction The direction of the bullet.
     * @return "HIT" if a hit is detected, "MISS" otherwise.
     */
    public String reportHit(IWorld world, Position robotPosition, int bulletPosition, Direction direction) {
        Map<Integer, Robot> worldRobots = world.getRobots();
        List<Robot> robots = new ArrayList<>(worldRobots.values());
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

        for (Robot robot : robots) {
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
     * Setter for the enemyRobot.
     *
     * @param enemyRobot The enemy robot that was hit by the bullet.
     */
    public void setEnemyRobot(Robot enemyRobot) {
        this.enemyRobot = enemyRobot;
    }
}