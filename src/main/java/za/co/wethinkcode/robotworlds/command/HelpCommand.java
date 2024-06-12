package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        System.out.println("Available commands:");
        System.out.println("   launch [make] [name] - launch robot into world");
        System.out.println("   look                 - look around in robot's field of view");
        System.out.println("   state                - robot state");
        System.out.println("   forward [steps]      - move robot forward");
        System.out.println("   back [steps]         - move robot backward");
        System.out.println("   turn [direction]     - turn robot left or right");
        System.out.println("   Orientation          - get robot's current orientation");
        System.out.println("   quit                 - quit the server");

        return new ServerResponse("OK",null,null);
    }

    }


