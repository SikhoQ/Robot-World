package za.co.wethinkcode.robotworlds.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientRequestTest {

    @Test
    void testClientRequestCreation() {
        // Test data
        String robotName = "TestRobot";
        String command = "MOVE";
        Object[] arguments = {5};

        // Create ClientRequest instance
        ClientRequest request = new ClientRequest(robotName, command, arguments);

        // Assert the fields
        assertEquals(robotName, request.robot(), "Robot name should be 'TestRobot'");
        assertEquals(command, request.command(), "Command should be 'MOVE'");
        assertArrayEquals(arguments, request.arguments(), "Arguments should match the provided array");
    }

    @Test
    void testClientRequestWithNoArguments() {
        // Test data
        String robotName = "TestRobot";
        String command = "STOP";
        Object[] arguments = {};

        // Create ClientRequest instance
        ClientRequest request = new ClientRequest(robotName, command, arguments);

        // Assert the fields
        assertEquals(robotName, request.robot(), "Robot name should be 'TestRobot'");
        assertEquals(command, request.command(), "Command should be 'STOP'");
        assertArrayEquals(arguments, request.arguments(), "Arguments should be an empty array");
    }

    @Test
    void testClientRequestWithNullArguments() {
        // Test data
        String robotName = "TestRobot";
        String command = "START";
        Object[] arguments = null;

        // Create ClientRequest instance
        ClientRequest request = new ClientRequest(robotName, command, arguments);

        // Assert the fields
        assertEquals(robotName, request.robot(), "Robot name should be 'TestRobot'");
        assertEquals(command, request.command(), "Command should be 'START'");
        assertNull(request.arguments(), "Arguments should be null");
    }


    @Test
    void testClientRequestWithEmptyCommand() {
        // Test data
        String robotName = "TestRobot";
        String command = "";
        Object[] arguments = {};

        // Create ClientRequest instance
        ClientRequest request = new ClientRequest(robotName, command, arguments);

        // Assert the fields
        assertEquals(robotName, request.robot(), "Robot name should be 'TestRobot'");
        assertEquals(command, request.command(), "Command should be an empty string");
        assertArrayEquals(arguments, request.arguments(), "Arguments should be an empty array");
    }

    @Test
    void testClientRequestWithNullCommand() {
        // Test data
        String robotName = "TestRobot";
        String command = null;
        Object[] arguments = {};

        // Create ClientRequest instance
        ClientRequest request = new ClientRequest(robotName, command, arguments);

        // Assert the fields
        assertEquals(robotName, request.robot(), "Robot name should be 'TestRobot'");
        assertNull(request.command(), "Command should be null");
        assertArrayEquals(arguments, request.arguments(), "Arguments should be an empty array");
    }

    @Test
    void testClientRequestWithMultipleArguments() {
        // Test data
        String robotName = "TestRobot";
        String command = "MOVE";
        Object[] arguments = {10, "north", true};

        // Create ClientRequest instance
        ClientRequest request = new ClientRequest(robotName, command, arguments);

        // Assert the fields
        assertEquals(robotName, request.robot(), "Robot name should be 'TestRobot'");
        assertEquals(command, request.command(), "Command should be 'MOVE'");
        assertArrayEquals(arguments, request.arguments(), "Arguments should match the provided array");
    }

    @Test
    void testClientRequestEquality() {
        // Test data
        String robotName = "TestRobot";
        String command = "MOVE";
        Object[] arguments = {5};

        // Create two identical ClientRequest instances
        ClientRequest request1 = new ClientRequest(robotName, command, arguments);
        ClientRequest request2 = new ClientRequest(robotName, command, arguments);

        // Assert that the two instances are equal
        assertEquals(request1, request2, "The two ClientRequest instances should be equal");
    }

    @Test
    void testClientRequestInequality() {
        // Test data
        String robotName1 = "TestRobot1";
        String robotName2 = "TestRobot2";
        String command = "MOVE";
        Object[] arguments = {5};

        // Create two different ClientRequest instances
        ClientRequest request1 = new ClientRequest(robotName1, command, arguments);
        ClientRequest request2 = new ClientRequest(robotName2, command, arguments);

        // Assert that the two instances are not equal
        assertNotEquals(request1, request2, "The two ClientRequest instances should not be equal");
    }


}