package za.co.wethinkcode.robotworlds.command;


import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

public class BackCommand extends Command {
    public BackCommand(String argument) {
        super("back", new Object[] {argument});
    }

    @Override
    public ServerResponse execute(SimpleBot target, IWorld world) {
        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        int nrSteps = Integer.parseInt(String.valueOf(getArguments()[0]));
        String message = target.updatePosition(-nrSteps, world);

        data.put("message", message);

        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getGun().getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
