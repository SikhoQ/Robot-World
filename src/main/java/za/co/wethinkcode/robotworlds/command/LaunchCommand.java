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
 * This class represents a LaunchCommand that extends the Command class.
 * It is responsible for creating a new Robot in the world based on the provided JSON node.
 * It also overrides the execute method to provide a ServerResponse based on the target Robot and the world state.
 */
public class LaunchCommand extends Command {

    public LaunchCommand(Object[] arguments) {
        super("launch", arguments);
    }

    /**
     * Creates a new Robot in the world based on the provided JSON node.
     *
     * @param rootNode The JSON node containing the robot's information.
     * @param world The world in which the robot will be created.
     * @param PORT The port number for the robot.
     * @return The newly created Robot, or null if the JSON fields are not present.
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

    /**
     * Executes the command on the target Robot and returns a ServerResponse based on the world state.
     *
     * @param target The Robot on which the command will be executed.
     * @param world The world in which the command will be executed.
     * @return A ServerResponse containing the result, data, and state of the command execution.
     */
    @Override
    public ServerResponse execute (Robot target, IWorld world) {
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