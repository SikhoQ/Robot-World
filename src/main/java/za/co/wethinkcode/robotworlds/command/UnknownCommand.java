package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

public class UnknownCommand extends Command {
    public UnknownCommand() {
        super("unknown");
    }

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        return null;
    }
}
