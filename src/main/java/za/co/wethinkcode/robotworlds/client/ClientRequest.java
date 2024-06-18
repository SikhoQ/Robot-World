/**
 * Represents a request sent from the client to the server.
 * This class is used to encapsulate the details of a robot's command and its arguments.
 */
package za.co.wethinkcode.robotworlds.client;

public record ClientRequest(String robot, String command, Object[] arguments) {
}