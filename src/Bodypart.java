/**
 * this class is being used to track the current and previous position
 * of each body part of the snake. It is being used to correctly calculate
 * the position of the snake on the game board
 */
public class Bodypart {
    private static int instances = 0;
    private int _currentX;
    private int _currentY;
    private int _prevX;
    private int _prevY;

    /**
     * Creates a new bodypart object
     *
     * @param posY the Y value of the bodypart on the board
     * @param posX the X value of the bodypart on the board
     */
    Bodypart(int posY, int posX) {
        _currentY = posY;
        _currentX = posX;
        _prevY = _currentY;
        _prevX = _currentX;
        instances++;
        System.out.println(instances);
    }

    /**
     * Registers the position of the bodypart
     * @param posY the y value of the new position
     * @param posX the x value of the new position
     */
    public void registerPos(int posY, int posX) {
        _prevY = _currentY;
        _prevX = _currentX;
        _currentY = posY;
        _currentX = posX;
    }

    /**
     * @return Returns the current Y value of the body part position
     */
    public int getY() {
        return _currentY;
    }

    /**
     * @return Returns the current X value of the body part position
     */
    public int getX() {
        return _currentX;
    }

    /**
     * @return Returns the previous Y value of the body part position
     */
    public int getprevY() {
        return _prevY;
    }

    /**
     * @return Returns the previous X value of the body part position
     */
    public int getprevX() {
        return _prevX;
    }
}