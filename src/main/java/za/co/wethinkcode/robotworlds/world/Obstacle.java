package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;

public interface Obstacle {

    int getBottomLeftX();

    int getBottomLeftY();

    int getSize();

    boolean blocksPosition(Position position);

    boolean blocksPath(Position a, Position b);
}

