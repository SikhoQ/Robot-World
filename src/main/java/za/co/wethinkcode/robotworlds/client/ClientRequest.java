package za.co.wethinkcode.robotworlds.client;

public class ClientRequest {
    public ClientRequest(String robot, String command, String[] arguments) {
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
