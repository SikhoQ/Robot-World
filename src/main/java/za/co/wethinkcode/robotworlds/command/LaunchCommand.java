package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * The LaunchCommand class is responsible for handling the launch action for a robot.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class LaunchCommand extends Command {

    public LaunchCommand(Object[] arguments) {
        super("launch", arguments);
    }
    /**
     * Creates a new robot in the given world.
     *
     * @param rootNode the JsonNode containing the details of the robot to be created.
     * @param world the IWorld in which the robot will be created.
     * @param PORT the port number assigned to the robot.
     * @return the created SimpleBot instance.
     */

    public Robot createRobot(JsonNode rootNode, IWorld world, int PORT) {
        Optional<Map<String, Object>> jsonFieldsOptional = JsonUtility.getJsonFields(rootNode);
        if (jsonFieldsOptional.isPresent()) {
            Map<String, Object> jsonFields = jsonFieldsOptional.get();
            String name = (String) jsonFields.get("robot");
            String make = (String) super.getArguments()[0];
            String[] makeAndName = new String[] {name, make};
            int maximumShots = (int) super.getArguments()[2];
            return world.addRobotToWorld(makeAndName, maximumShots, PORT);
        } else {
            return null;
        }
    }

    @Override
<<<<<<< HEAD
    public ServerResponse execute(SimpleBot target, IWorld world) {
=======
    public ServerResponse execute (Robot target, IWorld world) {
>>>>>>> sikho
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
        state.put("shots", Gun.getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}

