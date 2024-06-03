package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

/**
 * The UnknownCommand class handles commands that are not recognized by the system.
 * This command is triggered when an unrecognized command is issued.
 */
public class UnknownCommand extends Command {

    /**
     * Constructs an UnknownCommand object with the command name set to "unknown".
     */
    public UnknownCommand() {
        super("unknown");
    }

    /**
     * Executes the UnknownCommand, which handles the case of an unrecognized command.
     * The current implementation returns null, as it may be intended to be overridden or extended.
     *
     * @param target The robot that the command is executed on.
     * @return Always returns null in the current implementation.
     */
    @Override
    public ServerResponse execute(Robot target) {
        return null;
    }
}
