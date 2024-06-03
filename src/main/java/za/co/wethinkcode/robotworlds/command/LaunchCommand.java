package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.command.Command;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.TextWorld;

import java.util.HashMap;
import java.util.Map;

public class LaunchCommand extends Command {
    public LaunchCommand(String make, String name) {
        super("launch", make, name);
    }

    public Robot createRobot(IWorld world) {
        String make = super.getArgument1();
        String name = super.getArgument2();
        return world.launchRobot(make, name);
    }

    @Override
    public ServerResponse execute (Robot target) {
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
