/**
 * this class is being used to track the current and previous position
 * of each body part of the snake. It is being used to correctly calculate
 * the position of the snake on the game board
 */
class Bodypart
/**
 * Creates a new bodypart object
 *
 * @param posY the Y value of the bodypart on the board
 * @param posX the X value of the bodypart on the board
 */
internal constructor(posY: Int, posX: Int) {
    /**
     * @return Returns the current X value of the body part position
     */
    var x: Int = 0
        private set
    /**
     * @return Returns the current Y value of the body part position
     */
    var y: Int = 0
        private set
    private var prevX: Int = 0
    private var prevY: Int = 0

    init {
        y = posY
        x = posX
        prevY = y
        prevX = x
    }

    /**
     * Registers the position of the bodypart
     * @param posY the y value of the new position
     * @param posX the x value of the new position
     */
    fun registerPos(posY: Int, posX: Int) {
        prevY = y
        prevX = x
        y = posY
        x = posX
    }

    /**
     * @return Returns the previous Y value of the body part position
     */
    fun getprevY(): Int {
        return prevY
    }

    /**
     * @return Returns the previous X value of the body part position
     */
    fun getprevX(): Int {
        return prevX
    }
}