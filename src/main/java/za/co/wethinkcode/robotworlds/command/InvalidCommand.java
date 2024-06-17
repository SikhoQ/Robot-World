package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * The InvalidCommand class is used to handle cases where an invalid command is issued.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class InvalidCommand extends Command {
    private final String error;
    /**
     * Constructor for InvalidCommand.
     *
     * @param error the error message indicating the type of invalid command.
     */
    public InvalidCommand(String error) {
        super("unknown", null);
        this.error = error;
    }

    /**
     * Executes the invalid command, returning an appropriate error message.
     *
     * @param target the SimpleBot that issued the command.
     * @param world the IWorld in which the command was issued.
     * @return a ServerResponse containing the error result and message.
     */
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
