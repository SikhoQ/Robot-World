package za.co.wethinkcode.robotworlds.robot;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;

public class SniperBot extends SimpleBot {

    public SniperBot(String name, Position position, Direction direction) {
        super(name, position, direction);
        this.shields = 1;
    }
}
