package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import java.util.HashMap;
import java.util.Map;

/**
 * The OrientationCommand class represents a command to retrieve the current orientation of a SimpleBot.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class OrientationCommand extends Command {
    public OrientationCommand() {
        super("orientation", null);
    }

    /**
     * Executes the orientation command.
     *
     * <p>This method retrieves the current orientation of the target robot and constructs a ServerResponse
     * containing the result "OK" along with the orientation data.</p>
     *
     * @param target the Robot object whose orientation is to be retrieved.
     * @param world the world context in which the command is executed.
     * @return a ServerResponse containing the result "OK" and the current orientation of the robot.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> state = new HashMap<>();
        state.put("direction", target.getDirection());

        return new ServerResponse(result, data, state);
    }
}
