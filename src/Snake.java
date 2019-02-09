import java.util.Random;
import java.util.List;
import java.util.LinkedList;

/**
 * The use of the snake class is to track the current position of the snake on the game board
 */
public class Snake {
    private final int _BOARDHEIGHT;
    private final int _BOARDWIDTH;
    private List<Bodypart> _snakebody;
    private Direction _direction;

    /**
     * Creates a Snake object
     */
    public Snake(int boardHeight, int boardWidth) {
        _BOARDHEIGHT = boardHeight;
        _BOARDWIDTH = boardWidth;
        _snakebody = new LinkedList<Bodypart>();
        genStartPos();
    }

    /**
     * Randomly generates a starting position for the snake
     */
    private void genStartPos() {
        Random random = new Random();
        Bodypart head = new Bodypart(random.nextInt(_BOARDHEIGHT - 1) + 1,
                random.nextInt(_BOARDWIDTH - 1) + 1);
        _snakebody.add(head);
        _direction = Direction.WEST;
    }

    /**
     * Moves the snake one field forward on the game board
     * depending on the current direction
     */
    public void move() {
        Bodypart head = _snakebody.get(0);

        switch(_direction)
        {
            case NORTH:
                head.registerPos(head.getY() - 1, head.getX());
                break;
            case SOUTH:
                head.registerPos(head.getY() + 1, head.getX());
                break;
            case WEST:
                head.registerPos(head.getY(), head.getX() - 1);
                break;
            case EAST:
                head.registerPos(head.getY(), head.getX() + 1);
                break;
        }
        adjustPosValues();
    }

    /**
     * Sets the direction of the snake
     * @param direction Direction in which the snake should move
     */
    public void setDirection(Direction direction)
    {
        _direction = direction;
    }

    /**
     *
     * @return Returns the current direction of the snake
     */
    public Direction getDirection()
    {
        return _direction;
    }

    /**
     * Increases the length of the snake's body by 1
     * TODO: Implement a data structure that avoids creating new bodypart objects
     */
    public void increaseLength() {
        Bodypart lastElement = _snakebody.get(_snakebody.size() - 1);
        _snakebody.add(new Bodypart(lastElement.getprevY(), lastElement.getprevX()));
    }

    /**
     * Adjusts the x and y values of each body part (except the head)
     * after the snake has been moved
     */
    private void adjustPosValues() {
        if (_snakebody.size() > 1) {
            for (int i = 1; i < _snakebody.size(); i++) {
                Bodypart predecessor = _snakebody.get(i - 1);
                _snakebody.get(i).registerPos(predecessor.getprevY(), predecessor.getprevX());
            }
        }
    }

    /**
     * @return Returns the list with the body parts of the snake
     */
    public List<Bodypart> getBodyparts() {
        return _snakebody;
    }
}
