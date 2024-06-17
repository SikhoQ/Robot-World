package za.co.wethinkcode.robotworlds.robot;

public class Gun {
    private final int MAX_SHOTS;
    private static int numberOfShots;

    public Gun(int numberOfShots) {
        Gun.numberOfShots = numberOfShots;
        this.MAX_SHOTS = numberOfShots;
    }

    public int getShotDistance() {
        return new int[] {0, 5, 4, 3, 2, 1}[MAX_SHOTS];
    }

    public static int getNumberOfShots() {
        return numberOfShots;
    }

    public int getMaximumShots() {
        return MAX_SHOTS;
    }

    public void fireShot() {
        if (numberOfShots > 0) {
            numberOfShots--;
        }
    }

    public void reload() {
        numberOfShots = MAX_SHOTS;
    }

    public void setNumberOfShots(int numberOfShots) {
        Gun.numberOfShots = numberOfShots;
    }
}
