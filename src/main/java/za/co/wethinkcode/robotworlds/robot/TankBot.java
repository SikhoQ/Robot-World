package za.co.wethinkcode.robotworlds.robot;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;

public class TankBot extends SimpleBot {
    public TankBot(String name, Position position, Direction direction, int PORT) {
        super(name, position, direction, PORT);
        this.shields = 100;
    }
}
