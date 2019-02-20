import java.awt.*
import java.awt.event.*
import java.util.Random
import javax.swing.*

/**
 * The GameBoard class manages the state of the playing area by setting tracking the objects that
 * are being placed on the board.
 * TODO: Optimize placement algorithm for snacks
 * TODO: Integrate a "press to start" button
 * TODO: Implement a "game over" message with a restart button
 */
class GameBoard : JPanel(), ActionListener, KeyListener {

    private val board: Array<Array<Field>>
    private val timer: Timer
    private val snake: Snake
    private var snackPosY: Int = 0
    private var snackPosX: Int = 0
    private var gameover: Boolean = false
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
        timer = Timer(DELAY, this)
        gameover = false
    }

    fun start() {
        timer.start()
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
        for (bodypart in snake.bodyparts) {
            val y = bodypart.y
            val x = bodypart.x
            resetField(bodypart.getprevY(), bodypart.getprevX())
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
     * Liste implementieren, die die Anzahl der leeren Felder beeinhaltet sowie deren Positionen zu dem Zeitpunkt
     * zu dem ein neuer Ort für einen Snack gesucht werden muss.
     */
    private fun setSnack() {
        var occupied = true
        while (occupied) {
            snackPosY = random.nextInt(BOARDHEIGHT - 1) + 1
            snackPosX = random.nextInt(BOARDWIDTH - 1) + 1
            if (board[snackPosY][snackPosX].state == FieldState.EMPTYFIELD) {
                board[snackPosY][snackPosX].state = FieldState.SNACKFIELD
                occupied = false
            }
        }
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
        val yPos = snake.bodyparts[0].y
        val xPos = snake.bodyparts[0].x
        when (snake.direction) {
            Direction.NORTH -> if (board[yPos - 1][xPos].state == FieldState.OCCUPIEDFIELD) {
                return false
            }
            Direction.WEST -> if (board[yPos][xPos - 1].state == FieldState.OCCUPIEDFIELD) {
                return false
            }
            Direction.SOUTH -> if (board[yPos + 1][xPos].state == FieldState.OCCUPIEDFIELD) {
                return false
            }
            Direction.EAST -> if (board[yPos][xPos + 1].state == FieldState.OCCUPIEDFIELD) {
                return false
            }
        }
        return true
    }

    override fun actionPerformed(e: ActionEvent) {
        if (fieldAheadClear()) {
            if (snackAhead()) {
                snake.increaseLength()
                setSnack()
            }

            snake.move()
            repaint()
        } else {
            timer.stop()
            gameover = true
            println("Game over")
        }
    }

    override fun keyTyped(e: KeyEvent) {

    }

    /**
     * Adjusts the direction of the snake according to the arrow key pressed
     *
     * @param e Key that has been pressed
     */
    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_LEFT -> snake.direction = Direction.WEST
            KeyEvent.VK_RIGHT -> snake.direction = Direction.EAST
            KeyEvent.VK_DOWN -> snake.direction = Direction.SOUTH
            KeyEvent.VK_UP -> snake.direction = Direction.NORTH
            KeyEvent.VK_ENTER -> if (gameover) {
                for (bodypart in snake.bodyparts) {
                    resetField(bodypart.y, bodypart.x)
                }
                snake.resetSnake()
                gameover = false
                start()
            }
        }
    }

    override fun keyReleased(e: KeyEvent) {

    }

    companion object {
        private val BOARDWIDTH = 22
        private val BOARDHEIGHT = 22
        private val FRAMEWIDTH = 230
        private val FRAMEHEIGHT = 230
        private val DELAY = 100
    }

}