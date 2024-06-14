package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class InvalidCommand extends Command {
    private final String error;
    public InvalidCommand(String error) {
        super("unknown", null);
        this.error = error;
    }

    @Override
    public ServerResponse execute(SimpleBot target, IWorld world) {
        String result = "ERROR";
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> state = new HashMap<>() {};

        switch (error) {
            case "UNKNOWN COMMAND":
                data.put("message", "Unsupported command");
                break;
            case "BAD ARGUMENTS":
                data.put("message", "Could not parse arguments");
                break;
            case "NO SPACE":
                data.put("message", "No more space in this world");
                break;
            case "NAME TAKEN":
                data.put("message", "Too many of you in this world");
                break;
        }

        return new ServerResponse(result, data, state);
    }
}
