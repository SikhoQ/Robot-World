package za.co.wethinkcode.robotworlds.command;


import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * The BackCommand class is responsible for moving the robot backward.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class BackCommand extends Command {
    public BackCommand(String argument) {
        super("back", new Object[] {argument});
    }


    @Override
    public ServerResponse execute(SimpleBot target, IWorld world) {
        // result field of response
        String result = "OK";
        // data field of response
        Map<String, Object> data = new HashMap<>();
        // Get the number of steps to move backward
        int nrSteps = Integer.parseInt(String.valueOf(getArguments()[0]));
        // Update the robot's position and get the message
        String message = target.updatePosition(-nrSteps, world);

        data.put("message", message);

        // state field of response
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getGun().getNumberOfShots());
        state.put("status", target.getStatus());

        // Return the server response with the result, data, and state
        return new ServerResponse(result, data, state);
    }
}
