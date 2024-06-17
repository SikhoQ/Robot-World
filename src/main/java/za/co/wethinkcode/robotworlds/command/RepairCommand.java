package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

public class RepairCommand extends Command {
    public RepairCommand() {super("repair", null);}

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        String name = target.getName();
        String make = target.getClass().getSimpleName();

        target.setStatus("REPAIR");
        target.repair();
        System.out.println("\n"+name+" ("+make+") repairing shield...");

        try {
            Thread.sleep(target.getRepairTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(name+" ("+make+") done repairing");

        target.setStatus("NORMAL");
        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> state = new HashMap<>();

        data.put("message", "Done");

        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getGun().getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
