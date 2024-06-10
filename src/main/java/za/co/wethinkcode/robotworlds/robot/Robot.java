package za.co.wethinkcode.robotworlds.robot;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;

public interface Robot {
    String getStatus();
    void setStatus(String newStatus);
    Direction getDirection();
    void setDirection(Direction direction);
    String getName();
    int getShields();
    int getShots();
    Position getPosition();
    String updatePosition(int numSteps, IWorld world);
    boolean blocksPosition(Position position);
    void updateShields(int hit);
    void updateShots(int shotsFired);
    void reset();
}
