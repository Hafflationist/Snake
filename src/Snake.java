import java.util.Random;
/**
 *
 */
public class Snake {
    private int _length;
    private GameBoard _gameBoard;

    /**
     * Creates a Snake object
     */
    public Snake(GameBoard gameBoard)
    {
        _length = 1;
        _gameBoard = gameBoard;
        genStartPos();
    }

    /**
     * Randomly generates a starting position for the snake
     */
    private void genStartPos()
    {
        Random random = new Random();
        int posY = random.nextInt(_gameBoard.getBoardHeight()-1) + 1;
        int posX = random.nextInt(_gameBoard.getBoardWidth()-1)+1;
    }

    public void moveForward()
    {

    }

    public void turnLeft()
    {

    }

    public void turnRight()
    {

    }

    /**
     * @return the current length of the Snake
     */
    public int getLength()
    {
        return _length;
    }
}
