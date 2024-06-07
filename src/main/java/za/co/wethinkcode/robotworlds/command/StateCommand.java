package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

public class StateCommand extends Command {
    public StateCommand() {
        super("state");
    }

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        return null;
    }
}
