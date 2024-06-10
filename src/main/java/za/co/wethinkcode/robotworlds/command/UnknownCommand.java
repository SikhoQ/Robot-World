package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class UnknownCommand extends Command {
    public UnknownCommand() {
        super("unknown");
    }

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String result = "ERROR";
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> state = new HashMap<>() {};
        data.put("message", "Unsupported command");
        return new ServerResponse(result, data, state);
    }
}
