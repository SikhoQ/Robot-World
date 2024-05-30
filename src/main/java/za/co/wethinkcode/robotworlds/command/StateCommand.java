package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.command.Command;

public class StateCommand extends Command {
    public StateCommand() {
        super("state");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
