package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.HashMap;
import java.util.Map;

public class LeftCommand extends Command {

    public LeftCommand() {
        super("left");
    }

    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        switch (target.getDirection()){
            case NORTH:
                target.setDirection(Direction.WEST);
                break;
            case SOUTH:
                target.setDirection(Direction.EAST);
                break;
            case WEST:
                target.setDirection(Direction.SOUTH);
                break;
            case EAST:
                target.setDirection(Direction.NORTH);
                break;
        }

        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "DONE");
        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", target.getShots());
        state.put("status", target.getStatus());

        return new ServerResponse(result, data, state);
    }
}
