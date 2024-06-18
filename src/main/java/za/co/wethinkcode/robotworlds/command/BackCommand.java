package za.co.wethinkcode.robotworlds.command;


import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.Robot;
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

    /**
     * Executes the back command for the robot.
     *
     * @param target The robot that will execute the command.
     * @param world The world in which the robot operates.
     * @return A ServerResponse object containing the result, data, and state of the robot after executing the command.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        int nrSteps = Integer.parseInt(String.valueOf(getArguments()[0]));
        String message = target.updatePosition(-nrSteps, world);

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
