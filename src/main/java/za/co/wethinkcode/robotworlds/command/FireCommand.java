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

public class FireCommand extends Command {
    private Robot enemyRobot;
    protected int enemyRobotDistance = 0;

    public FireCommand() {
        super("fire", null);
    }

    @Override
<<<<<<< HEAD
    public ServerResponse execute(SimpleBot target, IWorld world) {
        target.getGun().fireShot();
=======
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
>>>>>>> sikho

        String result = "Ok";
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
            robotShotState.put("shots", enemyRobot.getGun().getNumberOfShots());
            robotShotState.put("status", enemyRobot.getStatus());

            data.put("state", robotShotState);
        }

        state.put("shots", target.getGun().getNumberOfShots());

        return new ServerResponse(result, data, state);
    }

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

    public Robot getEnemyRobot() {
        return enemyRobot;
    }

    public void setEnemyRobot(Robot enemyRobot) {
        this.enemyRobot = enemyRobot;
    }
}
