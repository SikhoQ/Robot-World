package za.co.wethinkcode.robotworlds.command;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for all commands that can be executed by a robot.
 * Subclasses must implement the execute method.
 */
public abstract class Command {
    private final String command;
    private final Object[] arguments;

    /**
     * Executes the command on the given target robot in the specified world.
     *
     * @param target the SimpleBot that will execute the command.
     * @param world the IWorld in which the robot operates.
     * @return a ServerResponse containing the result, data, and state after executing the command.
     */
    public abstract ServerResponse execute(SimpleBot target, IWorld world);
    /**
     * Constructor for Command.
     *
     * @param command the command name.
     * @param arguments the arguments for the command.
     */
    public Command(String command, Object[] arguments){
        this.command = command.trim();
        this.arguments = (arguments == null) ? new Object[] {} : arguments;
    }

    public String getCommand() {
        return command;
    }

    public Object[] getArguments() {
        return this.arguments;
    }

    /**
     * Factory method to create a Command instance from a JSON node.
     *
     * @param rootNode the root JSON node containing the command data.
     * @param world the IWorld in which the robot operates.
     * @return a Command instance based on the JSON data.
     */

    public static Command create(JsonNode rootNode, IWorld world) {
        Map<String, Object> jsonFields = Json.getJsonFields(rootNode);
        String name = (String) jsonFields.get("robot");
        String command = ((String) jsonFields.get("command")).toUpperCase();
        Object[] arguments = (Object[]) jsonFields.get("arguments");
        String validatedCommand = validateCommand(name, command, arguments, world);

        return switch (validatedCommand) {
            case "VALID COMMAND" -> switch (command.toUpperCase()) {
                case "LAUNCH" -> new LaunchCommand(arguments);
                case "LOOK" -> new LookCommand();
                case "STATE" -> new StateCommand();
                case "FORWARD" -> new ForwardCommand(String.valueOf(arguments[0]));
                case "BACK" -> new BackCommand(String.valueOf(arguments[0]));
                case "TURN" -> {
                    if (((String) arguments[0]).equalsIgnoreCase("RIGHT"))
                        yield new RightCommand();
                    else
                        yield new LeftCommand();
                }
                case "ORIENTATION" -> new OrientationCommand();
                case "FIRE" -> new FireCommand();
                case "RELOAD" -> new ReloadCommand();
                case "REPAIR" -> new RepairCommand();
                default -> new InvalidCommand("UNKNOWN COMMAND");
            };
            case "BAD ARGUMENTS" -> new InvalidCommand("BAD ARGUMENTS");
            default -> new InvalidCommand("UNKNOWN COMMAND");
        };
    }
    /**
     * Validates the command to ensure it is recognized and the arguments are correct.
     *
     * @param name the name of the robot.
     * @param command the command to be executed.
     * @param arguments the arguments for the command.
     * @param world the IWorld in which the robot operates.
     * @return a string indicating whether the command is valid, has bad arguments, or is unknown.
     */

    private static String validateCommand(String name, String command, Object[] arguments, IWorld world) {
        List<String> validCommands = new ArrayList<>(Arrays.asList("LAUNCH", "LOOK", "STATE", "FORWARD", "BACK", "TURN",
                                                                    "ORIENTATION", "FIRE", "RELOAD", "REPAIR"));
        List<String> validRobotMakes = new ArrayList<>(Arrays.asList("SIMPLEBOT", "SNIPERBOT"));

        if (!validCommands.contains(command))
            return "UNKNOWN COMMAND";

        switch (command) {
            case "LAUNCH":
                String robotMake = "";
                int shieldStrength = -1;
                int maximumShots = -1;
                if (arguments.length == 3) {
                    robotMake = (String) arguments[0];
                    shieldStrength = (int) arguments[1];
                    maximumShots = (int) arguments[2];
                }
                if (!validRobotMakes.contains(robotMake.toUpperCase()) ||
                        invalidRobotProperties(shieldStrength, maximumShots)) {
                    return "BAD ARGUMENTS";
                }
                break;
            case "FORWARD":
            case "BACK":
                int steps = -1;
                if (arguments.length == 1) {
                    try {
                        steps = Integer.parseInt(String.valueOf(arguments[0]));
                    } catch (IllegalArgumentException ignored) {}
                }
                if (steps < 0)
                    return "BAD ARGUMENTS";
                break;
            case "TURN":
                String direction = "";
                if (arguments.length == 1) {
                    direction = String.valueOf(arguments[0]);
                }
                if (!(direction.equalsIgnoreCase("LEFT") ||
                    direction.equalsIgnoreCase("RIGHT")))
                    return "BAD ARGUMENTS";

        }
        return "VALID COMMAND";
    }
    /**
     * Checks if the robot properties (shield strength and maximum shots) are invalid.
     *
     * @param shieldStrength the shield strength of the robot.
     * @param maximumShots the maximum number of shots the robot can have.
     * @return true if the properties are invalid, false otherwise.
     */

    private static boolean invalidRobotProperties(int shieldStrength, int maximumShots) {
        return shieldStrength < 0 || maximumShots < 0;
    }

    /**
     * Checks if the robot name is invalid (already exists or is empty/blank).
     *
     * @param robotName the name of the robot.
     * @param world the IWorld in which the robot operates.
     * @return true if the robot name is invalid, false otherwise.
     */

    private static boolean invalidRobotName(String robotName, IWorld world) {
        for (Map.Entry<Integer, SimpleBot> entry: world.getRobots().entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(robotName)) {
                return true;
            }
        }
        return robotName.isEmpty() || robotName.isBlank();
    }
}
