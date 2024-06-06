package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

/**
 * The StateCommand class provides information about the current state of the robot.
 * This command is triggered by typing "state".
 */
public class StateCommand extends Command {

    /**
     * Constructs a StateCommand object with the command name set to "state".
     */
    public StateCommand() {
        super("state");
    }

    /**
     * Executes the StateCommand, which provides the current state of the robot.
     * The current implementation returns null, as it may be intended to be overridden or extended.
     *
     * @param target The robot that the command is executed on.
     * @return Always returns null in the current implementation.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        return null;
    }
}

