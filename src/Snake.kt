import java.util.LinkedList

data class Bodypart(val y: Int, val x: Int, val direction: Direction)

/**
 * The use of the snake class is to track the current position of the snake on the game board
 */
class Snake(private val BOARDHEIGHT: Int, private val BOARDWIDTH: Int) {
    private val snakebody: MutableList<Bodypart>
    var direction: Direction = Direction.NORTH
    val bodyparts: List<Bodypart>
        get() = snakebody

    var lastBodypart: Bodypart? = null

    init {
        snakebody = LinkedList()
        resetSnake()
    }

    fun resetSnake() {
        if (!snakebody.isEmpty()) {
            snakebody.clear()
        }
        val head = Bodypart(BOARDHEIGHT / 2, BOARDWIDTH / 2, Direction.WEST)
        snakebody.add(head)
        direction = Direction.WEST
    }

    fun getNextHeadPos(direction: Direction): Pair<Int, Int> {
        val head = snakebody[0]
        return when (direction) {
            Direction.NORTH -> head.y - 1 to head.x
            Direction.SOUTH -> head.y + 1 to head.x
            Direction.WEST -> head.y to head.x - 1
            Direction.EAST -> head.y to head.x + 1
        }
    }

    /**
     * Moves the snake one field forward on the game board
     * depending on the current direction
     */
    fun move() {
        println(snakebody)
        val head = snakebody[0]
        val newHead = when (direction) {
            Direction.NORTH -> Bodypart(head.y - 1, head.x, direction)
            Direction.SOUTH -> Bodypart(head.y + 1, head.x, direction)
            Direction.WEST -> Bodypart(head.y, head.x - 1, direction)
            Direction.EAST -> Bodypart(head.y, head.x + 1, direction)
        }
        snakebody.add(0, newHead)
        lastBodypart = snakebody[snakebody.size - 1]
        snakebody.removeAt(snakebody.size - 1)
    }

    /**
     * Increases the length of the snake's body by 1
     * TODO: Implement a data structure that avoids creating new bodypart objects
     */
    fun increaseLength() {
        val tail = snakebody[snakebody.size - 1]
        val newTail = when (direction) {
            Direction.NORTH -> Bodypart(tail.y + 1, tail.x, direction)
            Direction.SOUTH -> Bodypart(tail.y - 1, tail.x, direction)
            Direction.WEST -> Bodypart(tail.y, tail.x + 1, direction)
            Direction.EAST -> Bodypart(tail.y, tail.x - 1, direction)
        }
        snakebody.add(newTail)
    }
}
