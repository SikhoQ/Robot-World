package za.co.wethinkcode.robotworlds.client;

public record ClientRequest(String robot, String command, Object[] arguments) {
}
