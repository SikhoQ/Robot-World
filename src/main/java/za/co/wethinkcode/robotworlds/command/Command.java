package za.co.wethinkcode.robotworlds.command;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.Robot;

public abstract class Command {
    private final String name;
    private String argument1;
    private String argument2;

    public abstract boolean execute(Robot target);

    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument1 = "";
        this.argument2 = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument1 = argument.trim();
        this.argument2 = "";
    }

    public Command(String name, String argument1, String argument2) {
        this(name);
        this.argument1 = argument1.trim();
        this.argument2 = argument2.trim();
    }

    public String getName() {
        return name;
    }

    public String getArgument1() {
        return this.argument1;
    }

    public String getArgument2() {
        return this.argument2;
    }

    public static Command create(JsonNode rootNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        String name = rootNode.get("robot").asText();
        String command = rootNode.get("command").asText();
        String[] arguments;

        try {
            arguments = objectMapper.treeToValue(rootNode.get("arguments"), String[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return switch (command.toUpperCase()) {
            case "LAUNCH" -> new LaunchCommand(arguments[0], name);
            case "LOOK" -> new LookCommand();
            case "STATE" -> new StateCommand();
            default -> throw new IllegalArgumentException();
        };
    }
}
