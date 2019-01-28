import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 * The GameBoard class manages the state of the playing area by setting tracking the objects that
 * are being placed on the board.
 */
public class GameBoard extends KeyAdapter implements ActionListener {

    private int[][] _board;
    private int _boardWidth;
    private int _boardHeight;
    private static final int _BORDERFIELD = 0;
    private static final int _OCCUPIEDFIELD = 1;
    private static final int _EMPTYFIELD = 2;
    private static final int _SNACKFIELD = 3;
    private static final int DELAY = 2000;
    private Timer _timer;
    private JFrame _mainWindow;
    private Snake _snake;

    /**
     * Creates a new GameBoard Object
     *
     * @param boardHeight desired height of the GameBoard
     * @param boardWidth  desired width of the GameBoard
     */
    public GameBoard(int boardHeight, int boardWidth) {
        _boardWidth = boardWidth + 2;
        _boardHeight = boardHeight + 2;
        _board = new int[_boardHeight][_boardWidth];
        _snake = new Snake(_boardHeight, _boardWidth);
        initBoard();
        initUI();
        _timer = new Timer(DELAY, this);
    }

    public void start() {
        _timer.start();
    }

    /**
     * Initializes the gameboard variables
     */
    private void initBoard() {
        for (int i = 1; i < _boardHeight - 1; i++) {
            for (int j = 1; j < _boardWidth - 1; j++) {
                _board[i][j] = _EMPTYFIELD;
            }
        }
        setSnack();
    }

    /**
     * Initializes the graphical user interface
     */
    private void initUI() {
        _mainWindow = new JFrame("Snake");
        _mainWindow.setSize(_boardWidth * 10, _boardWidth * 10);
        _mainWindow.setResizable(false);
        _mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _mainWindow.setLocationRelativeTo(null);
        _mainWindow.addKeyListener(this);
        _mainWindow.setVisible(true);
    }

    /**
     * Sets a snack to the desired position. A snack is marked by a value of 2
     */
    public void setSnack() {
        Random random = new Random();
        boolean occupied = true;
        while (occupied) {
            int yPos = random.nextInt(_boardHeight - 1) + 1;
            int xPos = random.nextInt(_boardWidth - 1) + 1;
            if (_board[yPos][xPos] == _EMPTYFIELD)
            {
                _board[yPos][xPos] = _SNACKFIELD;
                occupied = false;
            }
        }

    }

    /**
     * Sets the position of the snake on the game board
     */
    public void markPosition(int posY, int posX) throws IndexOutOfBoundsException {
        if (posY > 0 && posY < _boardHeight - 1 && posX > 0 && posX < _boardWidth - 1) {
            _board[posY][posX] = _OCCUPIEDFIELD;
        } else {
            throw new IndexOutOfBoundsException("markPosition: Index out of bounds!");
        }
    }

    /**
     * Resets the board values on the designated field to 1
     *
     * @param posY x-position of the field
     * @param posX y-position of the field
     */
    public void resetField(int posY, int posX) throws IndexOutOfBoundsException {
        if (posY > 0 && posY < _boardHeight - 1 && posX > 0 && posX < _boardWidth - 1) {
            _board[posY][posX] = _EMPTYFIELD;
        } else {
            throw new IndexOutOfBoundsException("resetField: Index out of bounds!");
        }
    }

    /**
     * Checks, if the snake is on a field with a snack
     */
    public boolean snackAhead() {
        int yPos = _snake.getBodyparts().get(0).getY();
        int xPos = _snake.getBodyparts().get(0).getX();
        Direction direction = _snake.getDirection();
        switch (direction) {
            case NORTH:
                if (_board[yPos - 1][xPos] == _SNACKFIELD) {
                    return true;
                }
                break;
            case WEST:
                if (_board[yPos][xPos - 1] == _SNACKFIELD) {
                    return true;
                }
                break;
            case SOUTH:
                if (_board[yPos + 1][xPos] == _SNACKFIELD) {
                    return true;
                }
                break;
            case EAST:
                if (_board[yPos][xPos + 1] == _SNACKFIELD) {
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * Checks if the next field in the moving direction is clear
     */
    public boolean fieldAheadClear() {
        int yPos = _snake.getBodyparts().get(0).getY();
        int xPos = _snake.getBodyparts().get(0).getX();
        Direction direction = _snake.getDirection();
        switch (direction) {
            case NORTH:
                if (_board[yPos - 1][xPos] < _EMPTYFIELD) {
                    return false;
                }
                break;
            case WEST:
                if (_board[yPos][xPos - 1] < _EMPTYFIELD) {
                    return false;
                }
                break;
            case SOUTH:
                if (_board[yPos + 1][xPos] < _EMPTYFIELD) {
                    return false;
                }
                break;
            case EAST:
                if (_board[yPos][xPos + 1] < _EMPTYFIELD) {
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * @return Returns the height of the playing area
     */
    public int getBoardHeight() {
        return _boardHeight - 2;
    }

    /**
     * @return Returns the width of the playing area
     */
    public int getBoardWidth() {
        return _boardWidth - 2;
    }

    /**
     * For debug purposes only
     * Prints the values of the gameboard
     */
    public void printBoard() {
        for (int i = 0; i < _boardHeight; i++) {
            for (int j = 0; j < _boardWidth; j++) {
                System.out.print(_board[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(fieldAheadClear()) {
            if(snackAhead()) {
                _snake.increaseLength();
                setSnack();
            }
            _snake.move();
            adjustSnakePos();
            printBoard();
        } else {
            _timer.stop();
            System.out.println("Game over");
        }
    }

    /**
     * Adjusts the direction of the snake according to the arrow key pressed
     *
     * @param e Key that has been pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_LEFT:
                _snake.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                _snake.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_DOWN:
                _snake.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_UP:
                _snake.setDirection(Direction.NORTH);
                break;
        }
    }

    /**
     * Adjusts the snake's position on the game board
     */
    private void adjustSnakePos() {
        for (Bodypart bodypart : _snake.getBodyparts()) {
            resetField(bodypart.getprevY(), bodypart.getprevX());
            markPosition(bodypart.getY(), bodypart.getX());
        }
    }

}
