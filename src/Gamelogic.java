import java.awt.event.*;

public class Gamelogic {

    private Snake _snake;
    private GameBoard _gameBoard;
    private UI _userinterface;
    private KeyEvent event;

    /**
     * Creates a new Gamelogic object
     */
    public Gamelogic() {
        _gameBoard = new GameBoard(20, 20);
        _snake = new Snake(_gameBoard.getBoardHeight(), _gameBoard.getBoardWidth());
        _userinterface = new UI(this);
        drawSnakePos();
    }

    public void move()
    {
        _snake.moveForward();
        drawSnakePos();
        _gameBoard.printBoard();
    }

    public void turnSnake(KeyEvent e) {
        System.out.println("KEY PRESSED");
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            //move left
            _snake.turnLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //move right
            _snake.turnRight();
        }
        drawSnakePos();
        _gameBoard.printBoard();
    }

    private void drawSnakePos() {
        for (Bodypart bodypart : _snake.getBodyparts()) {
            _gameBoard.resetField(bodypart.getprevY(), bodypart.getprevX());
            _gameBoard.markPosition(bodypart.getY(), bodypart.getX());
        }
    }

}