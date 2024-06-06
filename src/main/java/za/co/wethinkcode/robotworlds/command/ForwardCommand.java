package za.co.wethinkcode.robotworlds.command;


import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.HashMap;
import java.util.Map;

public class ForwardCommand extends Command {
    public ForwardCommand(String argument) {
        super("forward", argument);
    }

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        // result field of response
        String result = "OK";

        // data field of response
        Map<String, Object> data = new HashMap<>();
        String message = "";
        int nrSteps = Integer.parseInt(getArgument1());
        if (target.updatePosition(nrSteps))
            message = "Done";
        else
            message = "Obstructed";

        data.put("message", message);

        // state field of response
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getCurrentDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}

