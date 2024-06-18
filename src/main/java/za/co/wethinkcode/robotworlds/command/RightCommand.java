package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * The RightCommand class represents a command to turn a SimpleBot to the right.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class RightCommand extends Command {
    public RightCommand() {
        super("right", null);
    }

    /**
     * Executes the right command.
     *
     * <p>This method turns the target robot to the right by updating its direction according to the
     * current direction. It then constructs a ServerResponse containing the result "OK" along with
     * updated robot data and state information.</p>
     *
     * @param target the Robot object to turn to the right.
     * @param world the world context in which the command is executed.
     * @return a ServerResponse containing the result "OK" and updated robot data and state information.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        switch (target.getDirection()){
            case NORTH:
                target.setDirection(Direction.EAST);
                break;
            case SOUTH:
                target.setDirection(Direction.WEST);
                break;
            case WEST:
                target.setDirection(Direction.NORTH);
                break;
            case EAST:
                target.setDirection(Direction.SOUTH);
                break;
        }

        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Done");
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", Gun.getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
