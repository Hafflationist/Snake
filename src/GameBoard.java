import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 * The GameBoard class manages the state of the playing area by setting tracking the objects that
 * are being placed on the board.
 */
public class GameBoard extends JPanel implements ActionListener, KeyListener {

    private Field[][] _board;
    private int _boardWidth;
    private int _boardHeight;
    private static final int DELAY = 200;
    private Timer _timer;
    private JFrame _mainWindow;
    private Snake _snake;
    private int snackPosY;
    private int snackPosX;

    /**
     * Creates a new GameBoard Object
     *
     * @param boardHeight desired height of the GameBoard
     * @param boardWidth  desired width of the GameBoard
     */
    public GameBoard(int boardHeight, int boardWidth) {
        _boardWidth = boardWidth + 2;
        _boardHeight = boardHeight + 2;
        _board = new Field[_boardHeight][_boardWidth];
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
        for (int i = 0; i < _boardHeight; i++) {
            for (int j = 0; j < _boardWidth; j++) {
                _board[i][j] = new Field((i+1)*10 - 5, (j+1)*10 - 5);
                if (i == 0 || j == 0 || i == _boardHeight - 1 || j == _boardWidth - 1)
                {
                    _board[i][j].setState(FieldState.OCCUPIEDFIELD);
                } else {
                    _board[i][j].setState(FieldState.EMPTYFIELD);
                }
            }
        }
        setSnack();
    }

    /**
     * Initializes the graphical user interface
     */
    private void initUI() {
        _mainWindow = new JFrame("Snake");
        _mainWindow.setSize(_boardWidth * 10, _boardHeight * 10);
        _mainWindow.setResizable(false);
        _mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _mainWindow.setLocationRelativeTo(null);
        _mainWindow.addKeyListener(this);
        _mainWindow.add(this);
        setPreferredSize(new Dimension(_boardWidth * 10, _boardHeight * 10));
        _mainWindow.pack();
        _mainWindow.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        drawObjects(g);
    }

    /**
     * Sets a snack to the desired position. A snack is marked by a value of 2
     * TODO: Needs to be optimized
     */
    public void setSnack() {
        Random random = new Random();
        boolean occupied = true;
        while (occupied) {
            snackPosY = random.nextInt(_boardHeight - 1) + 1;
            snackPosX = random.nextInt(_boardWidth - 1) + 1;
            if (_board[snackPosY][snackPosX].getState() == FieldState.EMPTYFIELD) {
                _board[snackPosY][snackPosX].setState(FieldState.SNACKFIELD);
                occupied = false;
            }
        }
    }

    private void drawObjects(Graphics g)
    {
        for (Bodypart bodypart : _snake.getBodyparts()) {
            int y = bodypart.getY();
            int x = bodypart.getX();
            resetField(bodypart.getprevY(), bodypart.getprevX());
            markPosition(y, x);
            g.setColor(Color.GREEN);
            g.fillOval(_board[y][x].getFieldX(), _board[y][x].getFieldY(),10,10);
            g.setColor(Color.RED);
            g.fillOval(_board[snackPosY][snackPosX].getFieldX(),_board[snackPosY][snackPosX].getFieldY(), 10, 10);
        }
        repaint();
    }

    /**
     * Sets the position of the snake on the game board
     */
    public void markPosition(int posY, int posX) throws IndexOutOfBoundsException {
        if (posY > 0 && posY < _boardHeight - 1 && posX > 0 && posX < _boardWidth - 1) {
            _board[posY][posX].setState(FieldState.OCCUPIEDFIELD);
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
            _board[posY][posX].setState(FieldState.EMPTYFIELD);
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
                if (_board[yPos - 1][xPos].getState() == FieldState.SNACKFIELD) {
                    return true;
                }
                break;
            case WEST:
                if (_board[yPos][xPos - 1].getState() == FieldState.SNACKFIELD) {
                    return true;
                }
                break;
            case SOUTH:
                if (_board[yPos + 1][xPos].getState() == FieldState.SNACKFIELD) {
                    return true;
                }
                break;
            case EAST:
                if (_board[yPos][xPos + 1].getState() == FieldState.SNACKFIELD) {
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
                if (_board[yPos - 1][xPos].getState() == FieldState.OCCUPIEDFIELD) {
                    return false;
                }
                break;
            case WEST:
                if (_board[yPos][xPos - 1].getState() == FieldState.OCCUPIEDFIELD) {
                    return false;
                }
                break;
            case SOUTH:
                if (_board[yPos + 1][xPos].getState() == FieldState.OCCUPIEDFIELD) {
                    return false;
                }
                break;
            case EAST:
                if (_board[yPos][xPos + 1].getState() == FieldState.OCCUPIEDFIELD) {
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (fieldAheadClear()) {
            if (snackAhead()) {
                _snake.increaseLength();
                setSnack();
            }
            _snake.move();
            //adjustSnakePos();
            repaint();
        } else {
            _timer.stop();
            System.out.println("Game over");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

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

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
