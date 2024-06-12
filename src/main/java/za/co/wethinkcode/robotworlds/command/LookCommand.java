package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.command.Command;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

/**
 * The LookCommand class allows a robot to look around in its field of view.
 * This command is triggered by typing "look".
 */
public class LookCommand extends Command {

    /**
     * Constructs a LookCommand object with the command name set to "look".
     */
    public LookCommand() {
        super("look");
    }

    /**
     * Executes the LookCommand, which enables the robot to look around.
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
