package za.co.wethinkcode.robotworlds.server;

import java.io.File;
import java.io.IOException;

public class ServerStarter {
    public static void main(String[] args) throws IOException, InterruptedException {
        startServer();

        startClient();
    }

    private static void startServer() throws IOException, InterruptedException {
        String classpath = buildClasspath();

        ProcessBuilder serverProcessBuilder = new ProcessBuilder(
                "java",
                "-cp",
                classpath,
                "za.co.wethinkcode.robotworlds.server.RobotWorldServer",
                "8080" // Adjust the port number as needed
        );

        File workingDirectory = new File("/home/wtc/my_work/dbn_14_robot_worlds");
        serverProcessBuilder.directory(workingDirectory);

        Process serverProcess = serverProcessBuilder.start();

        serverProcess.waitFor();
    }

    private static void startClient() throws IOException, InterruptedException {
        String classpath = buildClasspath();

        ProcessBuilder clientProcessBuilder = new ProcessBuilder(
                "java",
                "-cp",
                classpath,
                "za.co.wethinkcode.robotworlds.client.RobotClient",
                "localhost",
                "8080" // Adjust the port number to match the server
        );

        File workingDirectory = new File("/home/wtc/my_work/dbn_14_robot_worlds");
        clientProcessBuilder.directory(workingDirectory);

        Process clientProcess = clientProcessBuilder.start();

        clientProcess.waitFor();
    }

    private static String buildClasspath() {
        StringBuilder classpathBuilder = new StringBuilder();
        classpathBuilder.append("/home/wtc/my_work/dbn_14_robot_worlds/target/classes");
        classpathBuilder.append(File.pathSeparator);

        String[] dependencies = {
                "com.fasterxml.jackson.core:jackson-annotations:2.14.1",
                "com.fasterxml.jackson.core:jackson-core:2.14.1",
                "com.fasterxml.jackson.core:jackson-databind:2.14.1",
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
