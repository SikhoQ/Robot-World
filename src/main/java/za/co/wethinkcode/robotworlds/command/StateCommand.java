package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class StateCommand extends Command {
    public StateCommand() {
        super("state", null);
    }

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String result = "OK";
        Map<String, Object> data = new HashMap<>() {};
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getGun().getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
