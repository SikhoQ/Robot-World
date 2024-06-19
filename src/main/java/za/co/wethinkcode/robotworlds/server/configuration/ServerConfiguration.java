package za.co.wethinkcode.robotworlds.server.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robotworlds.ConfigUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * The ServerConfiguration class handles the setup and configuration of the server,
 * including reading configuration parameters and setting up logging.
 */
public class ServerConfiguration {

    public static final String LOG_FILE_PATH = "/home/wtc/my_work/dbn_14_robot_worlds/server_log.txt";
    public static final String SERVER_CONFIG_FILE_PATH = "src/main/resources/serverConfig.json";

    /**
     * Configures the server by loading and prompting for necessary configuration parameters.
     */
    public static void configureServer() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode config = ConfigUtility.loadConfiguration();

        Scanner scanner = new Scanner(System.in);

        ObjectNode worldSize = (ObjectNode) config.get("worldSize");
        if (worldSize == null) {
            worldSize = objectMapper.createObjectNode();
            config.set("worldSize", worldSize);
        }

        if (!worldSize.has("width") || worldSize.get("width").asText().isEmpty()) {
            System.out.print("Enter world width: ");
            int width = Integer.parseInt(scanner.nextLine().trim());
            worldSize.put("width", width);
        }

        if (!worldSize.has("height") || worldSize.get("height").asText().isEmpty()) {
            System.out.print("Enter world height: ");
            int height = Integer.parseInt(scanner.nextLine().trim());
            worldSize.put("height", height);
        }

        if (!config.has("visibility") || config.get("visibility").asText().isEmpty()) {
            System.out.print("Enter visibility: ");
            int visibility = Integer.parseInt(scanner.nextLine().trim());
            config.put("visibility", visibility);
        }

        if (!config.has("reload") || config.get("reload").asText().isEmpty()) {
            System.out.print("Enter reload time (ms): ");
            int reload = Integer.parseInt(scanner.nextLine().trim());
            config.put("reload", reload);
        }

        if (!config.has("repair") || config.get("repair").asText().isEmpty()) {
            System.out.print("Enter repair time (ms): ");
            int repair = Integer.parseInt(scanner.nextLine().trim());
            config.put("repair", repair);
        }

        if (!config.has("shields") || config.get("shields").asText().isEmpty()) {
            System.out.print("Enter shields: ");
            int shields = Integer.parseInt(scanner.nextLine().trim());
            config.put("shields", shields);
        }

        saveConfiguration(objectMapper, config);
    }

    /**
     * Saves the configuration to a file.
     *
     * @param objectMapper the ObjectMapper used to write the configuration.
     * @param config the configuration to be saved.
     */
    private static void saveConfiguration(ObjectMapper objectMapper, ObjectNode config) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(SERVER_CONFIG_FILE_PATH), config);
        } catch (IOException e) {
            System.out.println("Failed to save configuration file.");
        }
    }

    /**
     * Sets up logging by redirecting System.out and System.err to both the console and a log file.
     *
     * @param logFilePath the path to the log file.
     */
    public static void setupLogging(String logFilePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(logFilePath);

            TeeOutputStream teeOut = new TeeOutputStream(System.out, fileOutputStream);
            TeeOutputStream teeErr = new TeeOutputStream(System.err, fileOutputStream);

            PrintStream psOut = new PrintStream(teeOut);
            PrintStream psErr = new PrintStream(teeErr);

            System.setOut(psOut);
            System.setErr(psErr);

            System.out.println("Console output is saving to: " + logFilePath);

        } catch (IOException e) {
            System.err.println("Error setting up logging");
            System.exit(1);
        }
    }
}
