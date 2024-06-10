package za.co.wethinkcode.robotworlds.client;

public class ClientRequest {
    private final String robot;
    private final String command;
    private final String[] arguments;

    public ClientRequest(String robot, String command, String[] arguments) {
        this.robot = robot;
        this.command = command;
        this.arguments = arguments;
    }

    public String getRobot() {
        return robot;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArguments() {
        return arguments;
    }
}
