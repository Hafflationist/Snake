import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer
import javax.swing.WindowConstants

/**
 * The GameBoard class manages the state of the playing area by setting tracking the objects that
 * are being placed on the board.
 * TODO: Optimize placement algorithm for snacks
 * TODO: Integrate a "press to start" button
 * TODO: Implement a "game over" message with a restart button
 */
class GameBoard : JPanel(), KeyListener {


    private val board: Array<Array<Field>>
    private val snake: Snake
    private var snackPosY: Int = 0
    private var snackPosX: Int = 0
    private var gameover: Boolean = false
    private var gamestarted: Boolean = false
    private val random: Random

    /**
     * Creates a new GameBoard Object
     */
    init {
        board = Array(BOARDHEIGHT) { i ->
            Array(BOARDWIDTH) { j ->
                val state = if (i == 0 || j == 0 || i == BOARDHEIGHT - 1 || j == BOARDWIDTH - 1) {
                    FieldState.OCCUPIEDFIELD
                } else {
                    FieldState.EMPTYFIELD
                }
                Field((i + 1) * 10 - 5, (j + 1) * 10 - 5, state)
            }
        }
        snake = Snake(BOARDHEIGHT, BOARDWIDTH)
        random = Random()
        initBoard()
        initUI()
        kotlin.concurrent.timer(startAt = Calendar.getInstance().time!!, period = DELAY) {
            if (gameover || !gamestarted) return@timer
            if (fieldAheadClear()) {
                if (snackAhead()) {
                    snake.increaseLength()
                    setSnack()
                }

                snake.move()
                repaint()
            } else {
                gameover = true
                println("Game over")
            }
        }

        gameover = false
    }

    /**
     * Initializes the gameboard variables
     */
    private fun initBoard() {
        setSnack()
    }

    /**
     * Initializes the graphical user interface
     */
    private fun initUI() {
        val _mainWindow = JFrame("Snake")
        _mainWindow.setSize(FRAMEWIDTH, FRAMEHEIGHT)
        _mainWindow.isResizable = false
        _mainWindow.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        _mainWindow.setLocationRelativeTo(null)
        _mainWindow.addKeyListener(this)
        _mainWindow.add(this)
        preferredSize = Dimension(FRAMEWIDTH, FRAMEHEIGHT)
        _mainWindow.pack()
        _mainWindow.isVisible = true
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        background = Color.BLACK
        drawObjects(g)
        drawBorders(g)
    }

    private fun drawObjects(g: Graphics) {
        val last = snake.lastBodypart
        if (last != null)
            resetField(last.y, last.x)
        snake.bodyparts.forEach { bodypart ->
            val y = bodypart.y
            val x = bodypart.x
            markPosition(y, x)
            g.color = Color.GREEN
            g.fillOval(board[y][x].fieldX, board[y][x].fieldY, 10, 10)
            g.color = Color.RED
            g.fillOval(board[snackPosY][snackPosX].fieldX, board[snackPosY][snackPosX].fieldY, 10, 10)
        }
    }

    /**
     * Draws the vertical and horizontal borders on the game board
     *
     * @param g
     */
    private fun drawBorders(g: Graphics) {
        g.color = Color.BLUE
        //Draw vertical borders
        for (i in 0 until BOARDHEIGHT) {
            g.fillRect(board[i][0].fieldX, board[i][0].fieldY, 10, 10)
            g.fillRect(board[i][BOARDWIDTH - 1].fieldX, board[i][BOARDWIDTH - 1].fieldY, 10, 10)
        }
        //Draw horizontal borders
        for (j in 1 until BOARDWIDTH - 1) {
            g.fillRect(board[0][j].fieldX, board[0][j].fieldY, 10, 10)
            g.fillRect(board[BOARDHEIGHT - 1][j].fieldX, board[BOARDHEIGHT - 1][j].fieldY, 10, 10)
        }
    }

    /**
     * Sets a snack to the desired position. A snack is marked by a value of 2
     * Liste implementieren, die die Anzahl der leeren Felder beinhaltet sowie deren Positionen zu dem Zeitpunkt
     * zu dem ein neuer Ort für einen Snack gesucht werden muss.
     */
    private tailrec fun setSnack() {
        snackPosY = random.nextInt(BOARDHEIGHT - 1) + 1
        snackPosX = random.nextInt(BOARDWIDTH - 1) + 1
        if (board[snackPosY][snackPosX].state == FieldState.EMPTYFIELD)
            board[snackPosY][snackPosX].state = FieldState.SNACKFIELD
        else
            setSnack()
    }

    /**
     * Sets the position of the snake on the game board
     */
    @Throws(IndexOutOfBoundsException::class)
    private fun markPosition(posY: Int, posX: Int) {
        if (posY > 0 && posY < BOARDHEIGHT - 1 && posX > 0 && posX < BOARDWIDTH - 1) {
            board[posY][posX].state = FieldState.OCCUPIEDFIELD
        } else {
            throw IndexOutOfBoundsException("markPosition: Index out of bounds!")
        }
    }

    /**
     * Resets the board values on the designated field to 1
     *
     * @param posY x-position of the field
     * @param posX y-position of the field
     */
    @Throws(IndexOutOfBoundsException::class)
    private fun resetField(posY: Int, posX: Int) {
        if (posY > 0 && posY < BOARDHEIGHT - 1 && posX > 0 && posX < BOARDWIDTH - 1) {
            board[posY][posX].state = FieldState.EMPTYFIELD
        } else {
            throw IndexOutOfBoundsException("resetField: Index out of bounds!")
        }
    }

    /**
     * Checks, if the snake is on a field with a snack
     */
    private fun snackAhead(): Boolean {
        val yPos = snake.bodyparts[0].y
        val xPos = snake.bodyparts[0].x
        when (snake.direction) {
            Direction.NORTH -> if (board[yPos - 1][xPos].state == FieldState.SNACKFIELD) {
                return true
            }
            Direction.WEST -> if (board[yPos][xPos - 1].state == FieldState.SNACKFIELD) {
                return true
            }
            Direction.SOUTH -> if (board[yPos + 1][xPos].state == FieldState.SNACKFIELD) {
                return true
            }
            Direction.EAST -> if (board[yPos][xPos + 1].state == FieldState.SNACKFIELD) {
                return true
            }
        }
        return false
    }

    /**
     * Checks if the next field in the moving direction is clear
     */
    private fun fieldAheadClear(): Boolean {
        val coords = snake.getNextHeadPos(snake.direction)
        return board[coords.first][coords.second].state != FieldState.OCCUPIEDFIELD
    }


    /**
     * Adjusts the direction of the snake according to the arrow key pressed
     *
     * @param e Key that has been pressed
     */
    override fun keyPressed(e: KeyEvent) {
        gamestarted = true
        when (e.keyCode) {
            KeyEvent.VK_LEFT -> snake.direction = Direction.WEST
            KeyEvent.VK_RIGHT -> snake.direction = Direction.EAST
            KeyEvent.VK_DOWN -> snake.direction = Direction.SOUTH
            KeyEvent.VK_UP -> snake.direction = Direction.NORTH
            KeyEvent.VK_ENTER -> if (gameover) {
                snake.bodyparts.forEach { resetField(it.y, it.x) }
                snake.resetSnake()
                gameover = false
            }
        }
    }

    override fun keyReleased(e: KeyEvent) {
    }

    override fun keyTyped(e: KeyEvent?) {
    }

    companion object {
        private val BOARDWIDTH = 22
        private val BOARDHEIGHT = 22
        private val FRAMEWIDTH = 230
        private val FRAMEHEIGHT = 230
        private val DELAY = 100L
    }

}