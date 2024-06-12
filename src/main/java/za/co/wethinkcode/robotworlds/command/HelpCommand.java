package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(Robot target) {
        System.out.println("Available commands:");
        System.out.println("   launch [make] [name] - launch robot into world");
        System.out.println("   look                 - look around in robot's field of view");
        System.out.println("   state                - robot state\n");

        return true;
    }
}
