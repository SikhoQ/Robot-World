package za.co.wethinkcode.robotworlds.command;


import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

public class ForwardCommand extends Command {
    public ForwardCommand(String argument) {
        super("forward", new Object[] {argument});
    }

    @Override
    public ServerResponse execute(SimpleBot target, IWorld world) {
        // result field of response
        String result = "OK";
        // data field of response
        Map<String, Object> data = new HashMap<>();
        int nrSteps = Integer.parseInt(String.valueOf(getArguments()[0]));
        String message = target.updatePosition(nrSteps, world);

        data.put("message", message);

        // state field of response
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getGun().getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
