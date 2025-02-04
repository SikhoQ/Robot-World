package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

/**
 * The HelpCommand class provides information about available commands to the user.
 * This command is triggered by typing "help".
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", null);
    }

    /**
     * Executes the HelpCommand which displays a list of available commands and their descriptions.
     *
     * @param target The robot that the command is executed on. This parameter is not used in this command.
     * @return Always returns null as there is no server response required for the help command.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        System.out.println("Available commands:");
        System.out.println("   launch [Bot type] [Bot name] - launch robot into world");
        System.out.println("   look                 - look around in robot's field of view");
        System.out.println("   state                - robot state\n");

        return null;
    }
}
