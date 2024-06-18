package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import java.util.HashMap;
import java.util.Map;

/**
 * The RepairCommand class represents a command to repair a SimpleBot's shield.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class RepairCommand extends Command {
    public RepairCommand() {
        super("repair", null);
    }

    /**
     * Executes the repair command.
     *
     * <p>This method repairs the shield of the target robot, updates its status, and waits for the repair time
     * to complete. It then constructs a ServerResponse containing the result "OK" along with updated robot
     * data and state information.</p>
     *
     * @param target the Robot object whose shield is to be repaired.
     * @param world the world context in which the command is executed.
     * @return a ServerResponse containing the result "OK" and updated robot data and state information.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String name = target.getName();
        String make = target.getClass().getSimpleName();

        target.setStatus("REPAIR");
        target.repair();
        System.out.println("\n" + name + " (" + make + ") repairing shield...");

        try {
            Thread.sleep(target.getRepairTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(name + " (" + make + ") done repairing");

        target.setStatus("NORMAL");
        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> state = new HashMap<>();

        data.put("message", "Done");

        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", Gun.getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
