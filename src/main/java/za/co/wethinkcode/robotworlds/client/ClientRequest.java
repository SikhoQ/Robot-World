package za.co.wethinkcode.robotworlds.client;

/**
 * The ClientRequest class encapsulates a request made by a client in the Robot Worlds application.
 * It includes the name of the robot making the request, the command to be executed, and any arguments associated with the command.
 */
public class ClientRequest {
    private final String robot;
    private final String command;
    private final String[] arguments;

    /**
     * Constructs a new ClientRequest with the specified robot name, command, and arguments.
     *
     * @param robot the name of the robot making the request
     * @param command the command to be executed
     * @param arguments an array of arguments associated with the command
     */
    public ClientRequest(String robot, String command, String[] arguments) {
        this.robot = robot;
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * Returns the name of the robot making the request.
     *
     * @return the name of the robot
     */
    public String getRobot() {
        return robot;
    }

    /**
     * Returns the command to be executed.
     *
     * @return the command to be executed
     */
    public String getCommand() {
        return command;
    }

    /**
     * Returns the arguments associated with the command.
     *
     * @return an array of arguments associated with the command
     */
    public String[] getArguments() {
        return arguments;
    }
}

