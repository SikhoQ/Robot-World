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

public abstract class Command {
    private final String command;
    private final Object[] arguments;
    public abstract ServerResponse execute(SimpleBot target, IWorld world);

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
//                case "REPAIR" -> new RepairCommand();
                default -> new InvalidCommand("UNKNOWN COMMAND");
            };
            case "BAD ARGUMENTS" -> new InvalidCommand("BAD ARGUMENTS");
            default -> new InvalidCommand("UNKNOWN COMMAND");
        };
    }

    private static String validateCommand(String name, String command, Object[] arguments, IWorld world) {
        List<String> validCommands = new ArrayList<>(Arrays.asList("LAUNCH", "LOOK", "STATE", "FORWARD",
                                                                    "BACK", "TURN", "ORIENTATION", "FIRE", "RELOAD", "REPAIR"));
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

    private static boolean invalidRobotProperties(int shieldStrength, int maximumShots) {
        return shieldStrength < 0 || maximumShots < 0;
    }

    private static boolean invalidRobotName(String robotName, IWorld world) {
        for (Map.Entry<Integer, SimpleBot> entry: world.getRobots().entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(robotName)) {
                return true;
            }
        }
        return robotName.isEmpty() || robotName.isBlank();
    }
}
