import java.util.LinkedList

/**
 * The use of the snake class is to track the current position of the snake on the game board
 */
class Snake(private val BOARDHEIGHT: Int, private val BOARDWIDTH: Int) {
    private val snakebody: MutableList<Bodypart>
    /**
     * @return Returns the current direction of the snake
     */
    /**
     * Sets the direction of the snake
     *
     * @param direction Direction in which the snake should move
     */
    var direction: Direction? = null

    /**
     * @return Returns the list with the body parts of the snake
     */
    val bodyparts: List<Bodypart>
        get() = snakebody

    init {
        snakebody = LinkedList()
        resetSnake()
    }

    fun resetSnake() {
        if (!snakebody.isEmpty()) {
            snakebody.clear()
        }
        val head = Bodypart(BOARDHEIGHT / 2, BOARDWIDTH / 2)
        snakebody.add(head)
        direction = Direction.WEST
    }

    /**
     * Moves the snake one field forward on the game board
     * depending on the current direction
     */
    fun move() {
        val head = snakebody[0]

        when (direction) {
            Direction.NORTH -> head.registerPos(head.y - 1, head.x)
            Direction.SOUTH -> head.registerPos(head.y + 1, head.x)
            Direction.WEST -> head.registerPos(head.y, head.x - 1)
            Direction.EAST -> head.registerPos(head.y, head.x + 1)
        }
        adjustPosValues()
    }

    /**
     * Increases the length of the snake's body by 1
     * TODO: Implement a data structure that avoids creating new bodypart objects
     */
    fun increaseLength() {
        val lastElement = snakebody[snakebody.size - 1]
        snakebody.add(Bodypart(lastElement.getprevY(), lastElement.getprevX()))
    }

    /**
     * Adjusts the x and y values of each body part (except the head)
     * after the snake has been moved
     */
    private fun adjustPosValues() {
        if (snakebody.size > 1) {
            for (i in 1 until snakebody.size) {
                val predecessor = snakebody[i - 1]
                snakebody[i].registerPos(predecessor.getprevY(), predecessor.getprevX())
            }
        }
    }
}
