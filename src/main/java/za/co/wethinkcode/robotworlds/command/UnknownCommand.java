package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;

public class UnknownCommand extends Command {
    public UnknownCommand() {
        super("unknown");
    }

    @Override
    public boolean execute(Robot target) {
        return true;
    }
}
