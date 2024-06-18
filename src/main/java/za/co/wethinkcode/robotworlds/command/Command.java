package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Command {
    private final String command;
    private final Object[] arguments;

    public abstract ServerResponse execute(Robot target, IWorld world);

    /**
     * Constructor for the Command class.
     *
     * @param command The command name. It should be trimmed for leading and trailing spaces.
     * @param arguments The arguments for the command. If null, it will be initialized as an empty array.
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
     * Creates a new Command object based on the provided JSON node and the world context.
     *
     * <p>This method first extracts the JSON fields from the provided rootNode using
     * {@code JsonUtility.getJsonFields}. If the extraction is successful, it proceeds to
     * validate the command and its arguments. Based on the validated command, it returns
     * an appropriate Command object.</p>
     *
     * @param rootNode the JSON node containing the command details. It must have the following structure:
     * <pre>
     * {
     *   "robot": "robotName",
     *   "command": "COMMAND_NAME",
     *   "arguments": ["arg1", "arg2", ...]
     * }
     * </pre>
     * @param world the world context in which the command will be executed.
     * @return a Command object based on the provided JSON input and the world context.
     * Possible return values include instances of LaunchCommand, LookCommand, StateCommand,
     * ForwardCommand, BackCommand, RightCommand, LeftCommand, OrientationCommand, FireCommand,
     * ReloadCommand, RepairCommand, and InvalidCommand.
     */
    public static Command create(JsonNode rootNode, IWorld world) {
        Optional<Map<String, Object>> jsonFieldsOptional = JsonUtility.getJsonFields(rootNode);
        if (jsonFieldsOptional.isPresent()) {
            Map<String, Object> jsonFields = jsonFieldsOptional.get();
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
        } else {
            return new InvalidCommand("INVALID JSON FIELDS");
        }
    }

    /**
     * Validates the command and its arguments based on the provided parameters.
     *
     * @param name The name of the robot.
     * @param command The command to be validated.
     * @param arguments The arguments for the command.
     * @param world The world context in which the command will be executed.
     * @return A string indicating the validity of the command.
     *         Possible return values are: "VALID COMMAND", "BAD ARGUMENTS", "UNKNOWN COMMAND".
     */
    static String validateCommand(String name, String command, Object[] arguments, IWorld world) {
        List<String> validCommands = new ArrayList<>(Arrays.asList("LAUNCH", "LOOK", "STATE", "FORWARD", "BACK", "TURN",
                                                                    "ORIENTATION", "FIRE", "RELOAD", "REPAIR"));

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
                if (robotMake.isBlank() || robotMake.isEmpty()) {
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

    static boolean invalidRobotProperties(int shieldStrength, int maximumShots) {
        return shieldStrength < 0 || maximumShots < 0;
    }

    /**
     * Checks if a robot name is valid or not.
     *
     * <p>This method checks if the provided robot name is empty, blank, or already exists in the world.
     * If any of these conditions are met, it returns {@code true}. Otherwise, it returns {@code false}.
     *
     * @param robotName The name of the robot to be checked.
     * @param world The world context in which the robot will be created.
     * @return {@code true} if the robot name is invalid, {@code false} otherwise.
     */
    static boolean invalidRobotName(String robotName, IWorld world) {
        for (Map.Entry<Integer, Robot> entry: world.getRobots().entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(robotName)) {
                return true;
            }
        }
        return robotName.isEmpty() || robotName.isBlank();
    }
}
