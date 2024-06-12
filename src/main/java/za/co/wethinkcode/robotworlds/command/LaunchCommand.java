package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.command.Command;

public class LaunchCommand extends Command {
    public LaunchCommand(String make, String name) {
        super("launch", make, name);
    }

    @Override
    public boolean execute (Robot target) {
        return true;
    }
}
