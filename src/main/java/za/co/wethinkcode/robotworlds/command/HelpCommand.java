package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public ServerResponse execute(Robot target) {
        System.out.println("Available commands:");
        System.out.println("   launch [make] [name] - launch robot into world");
        System.out.println("   look                 - look around in robot's field of view");
        System.out.println("   state                - robot state\n");

        return null;
    }
}
