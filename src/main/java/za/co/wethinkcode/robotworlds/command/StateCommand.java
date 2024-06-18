package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * The StateCommand class represents a command to retrieve the current state of a SimpleBot.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class StateCommand extends Command {
    public StateCommand() {
        super("state", null);
    }


    /**
     * Executes the state command to retrieve the current state of the given target SimpleBot.
     * It retrieves the position, direction, shields, number of shots, and status of the SimpleBot
     * and returns them in the response.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String result = "OK";
        Map<String, Object> data = new HashMap<>() {};
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", Gun.getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
