package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.command.Command;

public class LookCommand extends Command {
    public LookCommand() {
        super("look");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
