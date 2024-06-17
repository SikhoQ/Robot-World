package za.co.wethinkcode.robotworlds.server;

import java.io.File;
import java.io.IOException;

public class ServerStarter {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Start the server in a separate process
        startServer();

        // Start the client in a separate process
        startClient();
    }

    private static void startServer() throws IOException, InterruptedException {
        // Build the classpath for the server
        String classpath = buildClasspath();

        // Construct the process builder for the server
        ProcessBuilder serverProcessBuilder = new ProcessBuilder(
                "java",
                "-cp",
                classpath,
                "za.co.wethinkcode.robotworlds.server.RobotWorldServer",
                "8080" // Adjust the port number as needed
        );

        // Set the working directory
        File workingDirectory = new File("/home/wtc/my_work/dbn_14_robot_worlds");
        serverProcessBuilder.directory(workingDirectory);

        // Start the server process
        Process serverProcess = serverProcessBuilder.start();

        // Optionally, wait for the server process to finish
        serverProcess.waitFor();
    }

    private static void startClient() throws IOException, InterruptedException {
        // Build the classpath for the client
        String classpath = buildClasspath();

        // Construct the process builder for the client
        ProcessBuilder clientProcessBuilder = new ProcessBuilder(
                "java",
                "-cp",
                classpath,
                "za.co.wethinkcode.robotworlds.client.RobotClient",
                "localhost",
                "8080" // Adjust the port number to match the server
        );

        // Set the working directory
        File workingDirectory = new File("/home/wtc/my_work/dbn_14_robot_worlds");
        clientProcessBuilder.directory(workingDirectory);

        // Start the client process
        Process clientProcess = clientProcessBuilder.start();

        // Optionally, wait for the client process to finish
        clientProcess.waitFor();
    }

    private static String buildClasspath() {
        // Construct the classpath dynamically using Maven dependencies
        StringBuilder classpathBuilder = new StringBuilder();
        classpathBuilder.append("/home/wtc/my_work/dbn_14_robot_worlds/target/classes");
        classpathBuilder.append(File.pathSeparator);

        // Add each dependency JAR to the classpath
        String[] dependencies = {
                "com.fasterxml.jackson.core:jackson-annotations:2.14.1",
                "com.fasterxml.jackson.core:jackson-core:2.14.1",
                "com.fasterxml.jackson.core:jackson-databind:2.14.1",
                // Add other dependencies here if needed
        };

        for (String dependency : dependencies) {
            String[] parts = dependency.split(":");
            String groupId = parts[0];
            String artifactId = parts[1];
            String version = parts[2];
            String jarPath = String.format("/home/wtc/.m2/repository/%s/%s/%s/%s-%s.jar",
                    groupId.replace('.', '/'), artifactId, version, artifactId, version);
            classpathBuilder.append(jarPath).append(File.pathSeparator);
        }

        return classpathBuilder.toString();
    }
}
