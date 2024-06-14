package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class Command {
    private final String command;
    private String argument1;
    private String argument2;

    public abstract ServerResponse execute(Robot target, IWorld world);

    public Command(String command){
        this.command = command.trim();
        this.argument1 = "";
        this.argument2 = "";
    }

    public Command(String command, String argument) {
        this(command);
        this.argument1 = argument.trim();
        this.argument2 = "";
    }

    public Command(String command, String argument1, String argument2) {
        this(command);
        this.argument1 = argument1.trim();
        this.argument2 = argument2.trim();
    }

    public String getCommand() {
        return command;
    }

    public String getArgument1() {
        return this.argument1;
    }

    public String getArgument2() {
        return this.argument2;
    }

    public static Command create(JsonNode rootNode, IWorld world) {
        ObjectMapper objectMapper = new ObjectMapper();
        String name = rootNode.get("robot").asText();
        String command = rootNode.get("command").asText().toUpperCase();
        String[] arguments = new String[] {};
        try {
            arguments = objectMapper.treeToValue(rootNode.get("arguments"), String[].class);
        } catch (JsonProcessingException ignored) {}

        String validatedCommand = validateCommand(command, arguments, world);

        return switch (validatedCommand) {
            case "VALID COMMAND" -> switch (command.toUpperCase()) {
                case "LAUNCH" -> new LaunchCommand(arguments[0], arguments[1]);
                case "LOOK" -> new LookCommand();
                case "STATE" -> new StateCommand();
                case "FORWARD" -> new ForwardCommand(String.valueOf(arguments[0]));
                case "BACK" -> new BackCommand(String.valueOf(arguments[0]));
                case "HELP" -> new HelpCommand();
                case "TURN" -> {
                    if (arguments[0].equalsIgnoreCase("RIGHT"))
                        yield new RightCommand();
                    else
                        yield new LeftCommand();
                }

                case "ORIENTATION" -> new OrientationCommand();
                default -> new InvalidCommand("UNKNOWN COMMAND");
            };
            case "BAD ARGUMENTS" -> new InvalidCommand("BAD ARGUMENTS");
            default -> new InvalidCommand("UNKNOWN COMMAND");
        };
    }

    private static String validateCommand(String command, String[] arguments, IWorld world) {
        List<String> validCommands = new ArrayList<>(Arrays.asList("LAUNCH", "LOOK", "STATE", "HELP",
                "FORWARD", "BACK", "TURN", "ORIENTATION"));
        List<String> validRobotMakes = new ArrayList<>(Arrays.asList("SIMPLEBOT", "SNIPERBOT"));

        if (!validCommands.contains(command))
            return "UNKNOWN COMMAND";

        switch (command) {
            case "LAUNCH":
                String robotMake = "";
                String robotName = "";
                if (arguments.length == 2) {
                    robotMake = arguments[0];
                    robotName = arguments[1];
                }
                if (!validRobotMakes.contains(robotMake.toUpperCase()) ||
                        invalidRobotName(robotName, world)) {
                    return "BAD ARGUMENTS";
                }
                break;
            case "FORWARD":
            case "BACK":
                int steps = -1;
                if (arguments.length == 1) {
                    try {
                        steps = Integer.parseInt(arguments[0]);
                    } catch (IllegalArgumentException ignored) {}
                }
                if (steps < 0)
                    return "BAD ARGUMENTS";
                break;
            case "TURN":
                String direction = "";
                if (arguments.length == 1) {
                    direction = arguments[0];
                }
                if (!(direction.equalsIgnoreCase("LEFT") ||
                        direction.equalsIgnoreCase("RIGHT")))
                    return "BAD ARGUMENTS";

        }
        return "VALID COMMAND";
    }

    private static boolean invalidRobotName(String robotName, IWorld world) {
        for (Map.Entry<Integer, Robot> entry: world.getRobots().entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(robotName)) {
                return true;
            }
        }
        return robotName.isEmpty() || robotName.isBlank();
    }

    public static String textcolor(String text, String color) {
        String ansiColor = "";
        switch (color.toLowerCase()) {
            case "red":
                ansiColor = "\u001B[31m";
                break;
            case "green":
                ansiColor = "\u001B[32m";
                break;
            case "yellow":
                ansiColor = "\u001B[33m";
                break;
            case "blue":
                ansiColor = "\u001B[34m";
                break;
            case "purple":
                ansiColor = "\u001B[35m";
                break;
            case "cyan":
                ansiColor = "\u001B[36m";
                break;
            case "white":
                ansiColor = "\u001B[37m";
                break;
            default:
                ansiColor = "\u001B[0m"; // Reset color
        }
        return ansiColor + text + "\u001B[0m";
    }
}
