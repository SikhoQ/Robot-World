package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * The ReloadCommand class represents a command to reload a SimpleBot's gun.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class ReloadCommand extends Command {
    public ReloadCommand() {super("reload", null);}

    @Override
    public ServerResponse execute(SimpleBot target, IWorld world) {
        try {
            Thread.sleep(target.getReloadTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        target.getGun().reload();
        target.setStatus("RELOAD");

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
