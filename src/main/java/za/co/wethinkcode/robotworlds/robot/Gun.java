package za.co.wethinkcode.robotworlds.robot;

public class Gun {
    private final int MAX_SHOTS;
    private int shotDistance;
    private int numberOfShots;

    public Gun(int numberOfShots) {
        this.numberOfShots = numberOfShots;
        this.MAX_SHOTS = numberOfShots;
        setShotDistance(numberOfShots);
    }

    public int getShotDistance() {
        return shotDistance;
    }

    public int getNumberOfShots() {
        return numberOfShots;
    }

    public int getMAX_SHOTS() {
        return MAX_SHOTS;
    }

    public void fireShot() {
        if (numberOfShots > 0) {
            numberOfShots--;
        }
    }

    public void reload() {
        this.numberOfShots = MAX_SHOTS;
    }

    private void setShotDistance(int numberOfShots) {
        int[] distance = new int[] {0, 5, 4, 3, 2, 1};
        this.shotDistance = distance[numberOfShots];
    }
}
