package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class InvalidCommand extends Command {
    private final String error;
    public InvalidCommand(String error) {
        super("unknown");
        this.error = error;
    }

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String result = "ERROR";
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> state = new HashMap<>() {};
        if (error.equals("UNKNOWN COMMAND"))
            data.put("message", "Unsupported command. Please type 'help' for available commands");
        else
            data.put("message", " Could you specify which direction you'd like me to turn? Please type 'turn <direction>'");
        return new ServerResponse(result, data, state);
    }
}
