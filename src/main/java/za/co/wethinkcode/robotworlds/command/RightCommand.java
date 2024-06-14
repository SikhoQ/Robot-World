package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

public class RightCommand extends Command {

    public RightCommand() {
        super("right", null);
    }

    @Override
    public ServerResponse execute(SimpleBot target, IWorld world) {
        switch (target.getDirection()){
            case NORTH:
                target.setDirection(Direction.EAST);
                break;
            case SOUTH:
                target.setDirection(Direction.WEST);
                break;
            case WEST:
                target.setDirection(Direction.NORTH);
                break;
            case EAST:
                target.setDirection(Direction.SOUTH);
                break;
        }

        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "DONE");
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getGun().getNumberOfShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}

