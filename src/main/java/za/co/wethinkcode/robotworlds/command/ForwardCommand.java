package za.co.wethinkcode.robotworlds.command;


import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * The ForwardCommand class is responsible for executing the forward movement action for a robot.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class ForwardCommand extends Command {
    public ForwardCommand(String argument) {
        super("forward", new Object[] {argument});
    }

    /**
     * Executes the forward command on the given target robot in the specified world.
     *
     * @param target the SimpleBot that will execute the command.
     * @param world the IWorld in which the robot operates.
     * @return a ServerResponse containing the result, data, and state after executing the command.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        int nrSteps = Integer.parseInt(String.valueOf(getArguments()[0]));
        String message = target.updatePosition(nrSteps, world);

        data.put("message", message);

        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", Gun.getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
