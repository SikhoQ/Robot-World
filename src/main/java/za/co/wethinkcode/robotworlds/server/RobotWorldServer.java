package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.maze.*;
import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.TextWorld;
import za.co.wethinkcode.robotworlds.Robot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class RobotWorldServer extends Thread{
    private static final int PORT = 5000;
    private static final List<RobotClientHandler> clients = new ArrayList<>();
    private static final ServerSocket serverSocket;

    static {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        /*ADD MEANINGFUL CODE FOR SERVER START-UP*/
    }

    public void shutdown() {
        // Disconnect all robots
        for (RobotClientHandler client: clients) {
            client.disconnectClient();
        }
        // Shut down the server
        closeServer();
        System.exit(0);
    }

    public void showWorldState(TextWorld world) {
        StringBuilder dump = new StringBuilder();

        List<Obstacle> obstacles = world.getObstacles();

        dump.append("Obstacles\n---------\n");

        if (!obstacles.isEmpty()) {
            dump.append("There are obstacles:");
        } else {
            dump.append("No obstacles");
        }

        for (Obstacle obstacle: obstacles) {
            String obstacleString = " - At ["+obstacle.getBottomLeftX()
                                    +","+obstacle.getBottomLeftY()+"] to ["
                                    +(obstacle.getBottomLeftX()+4)+","
                                    +(obstacle.getBottomLeftY()+4)+"]";
            dump.append(obstacleString).append("\n\n");
        }
        dump.append("Robots\n------\n");
        for (Robot robot: world.getRobots().keySet()) {
            String name = robot.getName();

            Position position = robot.getPosition();
            int xCoord = position.getX();
            int yCoord = position.getY();
            String positionString = "["+xCoord+","+yCoord+"]";

            Direction direction = robot.getCurrentDirection();
            String status = robot.getStatus();

            dump.append("Robot: ").append(name).append("\n");
            dump.append("Position: ").append(positionString).append("\n");
            dump.append("Direction: ").append(direction).append("\n");
            dump.append("State: ").append(status).append("\n\n");
        }

        System.out.println("World Dump:");
        System.out.println("===========\n");
        System.out.println(dump);

    }

    public void showRobots() {
        /*TODO*/
    }

    public static List<RobotClientHandler> getClients() {
        return clients;
    }

    private void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            // handle in calling code
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Maze maze = new SimpleMaze();
        TextWorld world = new TextWorld(maze);

        RobotWorldServer server = new RobotWorldServer();
        ServerConsole console = new ServerConsole(server, world);
        new Thread(console).start();

        try {
            System.out.println("Server started. Waiting for clients...");
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                RobotClientHandler clientHandler = new RobotClientHandler(clientSocket, world);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Server socket closed. Cannot accept new connections.");
        }
    }
}

