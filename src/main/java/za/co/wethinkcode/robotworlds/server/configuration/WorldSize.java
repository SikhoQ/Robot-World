package za.co.wethinkcode.robotworlds.server.configuration;

/**
 * The WorldSize class represents the dimensions of the world in terms of width and height.
 */
public class WorldSize {
    private static int width;
    private static int height;

    /**
     * Gets the width of the world.
     *
     * @return the width of the world.
     */
    public int getWidth() {
        return width==0?40:width;
    }

    /**
     * Sets the width of the world.
     *
     * @param width the width to set.
     */
    public void setWidth(int width) {
        WorldSize.width = width;
    }

    /**
     * Gets the height of the world.
     *
     * @return the height of the world.
     */
    public int getHeight() {
        return height==0?80:height;
    }

    /**
     * Sets the height of the world.
     *
     * @param height the height to set.
     */
    public void setHeight(int height) {
        WorldSize.height = height;
    }
}
