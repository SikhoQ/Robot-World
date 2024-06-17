package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;

public class SquareObstacle implements Obstacle {
    private final int bottomLeftX;
    private final int bottomLeftY;

    public SquareObstacle(int x, int y) {
        this.bottomLeftX = x;
        this.bottomLeftY = y;
    }

    public int getBottomLeftX() {
        return this.bottomLeftX;
    }

    public int getBottomLeftY() {
        return this.bottomLeftY;
    }

    public int getSize() {
        return 5;
    }

    public boolean blocksPosition(Position position) {
        return (position.getX() >= this.bottomLeftX && position.getX() <= this.bottomLeftX + 4)
                && (position.getY() >= this.bottomLeftY && position.getY() <= this.bottomLeftY + 4);
    }

    public boolean blocksPath(Position start, Position dest) {
        if (start.getX() == dest.getX()) {
            return (this.bottomLeftY >= Math.min(start.getY(), dest.getY())
                    && this.bottomLeftY <= Math.max(start.getY(), dest.getY()))
                    && (start.getX() >= bottomLeftX && start.getX() <= bottomLeftX + 4);
        }
        else {
            return (this.bottomLeftX >= Math.min(start.getX(), dest.getX())
                    && this.bottomLeftX <= Math.max(start.getX(), dest.getX()))
                    && (start.getY() >= bottomLeftY && start.getY() <= bottomLeftY + 4);
        }
    }

}
