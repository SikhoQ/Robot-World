package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

public class UnknownCommand extends Command {
    public UnknownCommand() {
        super("unknown");
    }

    @Override
    public ServerResponse execute(Robot target) {
        return null;
    }
}
