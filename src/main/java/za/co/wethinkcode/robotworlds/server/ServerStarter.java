package za.co.wethinkcode.robotworlds.server;

import java.io.File;
import java.io.IOException;

public class ServerStarter {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Start the server in a separate process
        startServer();

        // Start the server console in a separate process
        startServerConsole();
    }

    private static void startServer() throws IOException, InterruptedException {
        ProcessBuilder serverProcessBuilder = new ProcessBuilder(
                "java",
                "-cp",
                ".",
                "za.co.wethinkcode.robotworlds.server.RobotWorldServer",
                "<PORT>" // Replace <PORT> with the desired port number
        );

        // Set the working directory
        File workingDirectory = new File("path/to/your/project");
        serverProcessBuilder.directory(workingDirectory);

        // Start the server
        Process serverProcess = serverProcessBuilder.start();

        // Optionally, wait for the server process to finish
        serverProcess.waitFor();
    }

    private static void startServerConsole() throws IOException, InterruptedException {
        ProcessBuilder serverConsoleProcessBuilder = new ProcessBuilder(
                "java",
                "-cp",
                ".",
                "za.co.wethinkcode.robotworlds.server.ServerConsole"
        );

        // Set the working directory
        File workingDirectory = new File("path/to/your/project");
        serverConsoleProcessBuilder.directory(workingDirectory);

        // Start the server console
        Process serverConsoleProcess = serverConsoleProcessBuilder.start();

        // Optionally, wait for the server console process to finish
        serverConsoleProcess.waitFor();
    }
}
