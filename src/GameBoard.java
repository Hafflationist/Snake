import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 * The GameBoard class manages the state of the playing area by setting tracking the objects that
 * are being placed on the board.
 * TODO: Optimize placement algorithm for snacks
 * TODO: Integrate a "press to start" button
 * TODO: Implement a "game over" message with a restart button
 */
public class GameBoard extends JPanel implements ActionListener, KeyListener {

    private Field[][] board;
    private static final int BOARDWIDTH = 22;
    private static final int BOARDHEIGHT = 22;
    private static final int FRAMEWIDTH = 230;
    private static final int FRAMEHEIGHT = 230;
    private static final int DELAY = 100;
    private Timer timer;
    private Snake snake;
    private int snackPosY;
    private int snackPosX;
    private boolean gameRunning;
    private Random random;

    /**
     * Creates a new GameBoard Object
     */
    public GameBoard() {
        board = new Field[BOARDHEIGHT][BOARDWIDTH];
        snake = new Snake(BOARDHEIGHT, BOARDWIDTH);
        random = new Random();
        initBoard();
        initUI();
        timer = new Timer(DELAY, this);
        gameRunning = false;
    }

    private void start() {
        timer.start();
    }

    /**
     * Initializes the gameboard variables
     */
    private void initBoard() {
        for (int i = 0; i < BOARDHEIGHT; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                board[i][j] = new Field((i + 1) * 10 - 5, (j + 1) * 10 - 5);
                if (i == 0 || j == 0 || i == BOARDHEIGHT - 1 || j == BOARDWIDTH - 1) {
                    board[i][j].setState(FieldState.OCCUPIEDFIELD);
                } else {
                    board[i][j].setState(FieldState.EMPTYFIELD);
                }
            }
        }
        setSnack();
    }

    /**
     * Initializes the graphical user interface
     */
    private void initUI() {
        JFrame _mainWindow = new JFrame("Snake");
        _mainWindow.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        _mainWindow.setResizable(false);
        _mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _mainWindow.setLocationRelativeTo(null);
        _mainWindow.addKeyListener(this);
        _mainWindow.add(this);
        setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
        _mainWindow.pack();
        _mainWindow.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        drawObjects(g);
        drawBorders(g);
    }

    private void drawObjects(Graphics g) {
        for (Bodypart bodypart : snake.getBodyparts()) {
            int y = bodypart.getY();
            int x = bodypart.getX();
            resetField(bodypart.getprevY(), bodypart.getprevX());
            markPosition(y, x);
            g.setColor(Color.GREEN);
            g.fillOval(board[y][x].getFieldX(), board[y][x].getFieldY(), 10, 10);
            g.setColor(Color.RED);
            g.fillOval(board[snackPosY][snackPosX].getFieldX(), board[snackPosY][snackPosX].getFieldY(), 10, 10);
        }
    }

    /**
     * Draws the vertical and horizontal borders on the game board
     * @param g
     */
    private void drawBorders(Graphics g) {
        g.setColor(Color.BLUE);
        //Draw vertical borders
        for(int i = 0; i < BOARDHEIGHT; i++)
        {
            g.fillRect(board[i][0].getFieldX(), board[i][0].getFieldY(), 10, 10);
            g.fillRect(board[i][BOARDWIDTH - 1].getFieldX(), board[i][BOARDWIDTH - 1].getFieldY(), 10, 10);
        }
        //Draw horizontal borders
        for(int j = 1; j < BOARDWIDTH - 1; j++)
        {
            g.fillRect(board[0][j].getFieldX(), board[0][j].getFieldY(), 10, 10);
            g.fillRect(board[BOARDHEIGHT - 1][j].getFieldX(), board[BOARDHEIGHT - 1][j].getFieldY(), 10, 10);
        }
    }

    /**
     * Sets a snack to the desired position. A snack is marked by a value of 2
     * Liste implementieren, die die Anzahl der leeren Felder beeinhaltet sowie deren Positionen zu dem Zeitpunkt
     * zu dem ein neuer Ort für einen Snack gesucht werden muss.
     */
    private void setSnack() {
        boolean occupied = true;
        while (occupied) {
            snackPosY = random.nextInt(BOARDHEIGHT - 1) + 1;
            snackPosX = random.nextInt(BOARDWIDTH - 1) + 1;
            if (board[snackPosY][snackPosX].getState() == FieldState.EMPTYFIELD) {
                board[snackPosY][snackPosX].setState(FieldState.SNACKFIELD);
                occupied = false;
            }
        }
    }

    /**
     * Sets the position of the snake on the game board
     */
    private void markPosition(int posY, int posX) throws IndexOutOfBoundsException {
        if (posY > 0 && posY < BOARDHEIGHT - 1 && posX > 0 && posX < BOARDWIDTH - 1) {
            board[posY][posX].setState(FieldState.OCCUPIEDFIELD);
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
    private void resetField(int posY, int posX) throws IndexOutOfBoundsException {
        if (posY > 0 && posY < BOARDHEIGHT - 1 && posX > 0 && posX < BOARDWIDTH - 1) {
            board[posY][posX].setState(FieldState.EMPTYFIELD);
        } else {
            throw new IndexOutOfBoundsException("resetField: Index out of bounds!");
        }
    }

    /**
     * Checks, if the snake is on a field with a snack
     */
    private boolean snackAhead() {
        int yPos = snake.getBodyparts().get(0).getY();
        int xPos = snake.getBodyparts().get(0).getX();
        switch (snake.getDirection()) {
            case NORTH:
                if (board[yPos - 1][xPos].getState() == FieldState.SNACKFIELD) {
                    return true;
                }
                break;
            case WEST:
                if (board[yPos][xPos - 1].getState() == FieldState.SNACKFIELD) {
                    return true;
                }
                break;
            case SOUTH:
                if (board[yPos + 1][xPos].getState() == FieldState.SNACKFIELD) {
                    return true;
                }
                break;
            case EAST:
                if (board[yPos][xPos + 1].getState() == FieldState.SNACKFIELD) {
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * Checks if the next field in the moving direction is clear
     */
    private boolean fieldAheadClear() {
        int yPos = snake.getBodyparts().get(0).getY();
        int xPos = snake.getBodyparts().get(0).getX();
        switch (snake.getDirection()) {
            case NORTH:
                if (board[yPos - 1][xPos].getState() == FieldState.OCCUPIEDFIELD) {
                    return false;
                }
                break;
            case WEST:
                if (board[yPos][xPos - 1].getState() == FieldState.OCCUPIEDFIELD) {
                    return false;
                }
                break;
            case SOUTH:
                if (board[yPos + 1][xPos].getState() == FieldState.OCCUPIEDFIELD) {
                    return false;
                }
                break;
            case EAST:
                if (board[yPos][xPos + 1].getState() == FieldState.OCCUPIEDFIELD) {
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
                snake.increaseLength();
                setSnack();
            }

            snake.move();
            repaint();
        } else {
            timer.stop();
            gameRunning = false;
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                snake.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                snake.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_DOWN:
                snake.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_UP:
                snake.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_ENTER:
                if (!gameRunning) {
                    gameRunning = true;
                    start();
                }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
