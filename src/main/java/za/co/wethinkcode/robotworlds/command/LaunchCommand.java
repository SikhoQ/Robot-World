package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import java.util.HashMap;
import java.util.Map;

public class LaunchCommand extends Command {
    public LaunchCommand(Object[] arguments) {
        super("launch", arguments);
    }

    public SimpleBot createRobot(JsonNode rootNode, IWorld world, int PORT) {
        String name = (String) Json.getJsonFields(rootNode).get("robot");
        String make = (String) super.getArguments()[0];
        int maximumShots = (int) super.getArguments()[2];
        return world.launchRobot(make, name, maximumShots, PORT);
    }

    @Override
    public ServerResponse execute(SimpleBot target, IWorld world) {
        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        data.put("position", target.getPosition());
        data.put("visibility", world.getVisibility());
        data.put("reload", world.getReload());
        data.put("repair", world.getRepair());
        data.put("shields", world.getShields());
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getGun().getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}

