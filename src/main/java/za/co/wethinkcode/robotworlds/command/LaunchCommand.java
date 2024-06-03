package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.command.Command;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.TextWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * The LaunchCommand class is responsible for launching a robot into the world.
 * This command is triggered by typing "launch" followed by the make and name of the robot.
 */
public class LaunchCommand extends Command {

    /**
     * Constructs a LaunchCommand object with the specified make and name.
     *
     * @param make The make of the robot.
     * @param name The name of the robot.
     */
    public LaunchCommand(String make, String name) {
        super("launch", make, name);
    }

    /**
     * Creates a robot in the given world.
     *
     * @param world The world in which the robot will be launched.
     * @return The created robot.
     */
    public Robot createRobot(IWorld world) {
        String make = super.getArgument1();
        String name = super.getArgument2();
        return world.launchRobot(make, name);
    }

    /**
     * Executes the LaunchCommand which launches a robot into a new world and prepares a server response.
     *
     * @param target The robot that the command is executed on.
     * @return A ServerResponse object containing the result, data, and state of the robot.
     */
    @Override
    public ServerResponse execute(Robot target) {
        IWorld world = new TextWorld();
        // result field of response
        String result = "OK";
        // data field of response
        Map<String, Object> data = new HashMap<>();
        data.put("position", target.getPosition());
        data.put("visibility", world.getVisibility());
        data.put("reload", world.getReload());
        data.put("repair", world.getRepair());
        data.put("shields", world.getShields());
        // state field of response
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getCurrentDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
