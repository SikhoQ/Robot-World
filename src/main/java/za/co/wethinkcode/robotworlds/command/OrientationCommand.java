package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

public class OrientationCommand extends Command {
    public OrientationCommand() {
        super("orientation", null);
    }

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String result = "OK";
        Map<String, Object> data = new HashMap<>() {};
        Map<String, Object> state = new HashMap<>();
        state.put("direction", target.getDirection());

        return new ServerResponse(result, data, state);
    }
}
