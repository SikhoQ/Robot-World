package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.command.Command;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

public class StateCommand extends Command {
    public StateCommand() {
        super("state");
    }

    @Override
    public ServerResponse execute(Robot target) {
        return null;
    }
}
