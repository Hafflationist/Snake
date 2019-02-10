import java.util.List;
import java.util.LinkedList;

/**
 * The use of the snake class is to track the current position of the snake on the game board
 */
public class Snake {
    private final int BOARDHEIGHT;
    private final int BOARDWIDTH;
    private List<Bodypart> snakebody;
    private Direction direction;

    /**
     * Creates a Snake object
     */
    public Snake(int boardHeight, int boardWidth) {
        BOARDHEIGHT = boardHeight;
        BOARDWIDTH = boardWidth;
        snakebody = new LinkedList<Bodypart>();
        resetSnake();
    }

    public void resetSnake() {
        if (!snakebody.isEmpty()) {
            snakebody.clear();
        }
        Bodypart head = new Bodypart(BOARDHEIGHT / 2, BOARDWIDTH / 2);
        snakebody.add(head);
        direction = Direction.WEST;
    }

    /**
     * Moves the snake one field forward on the game board
     * depending on the current direction
     */
    public void move() {
        Bodypart head = snakebody.get(0);

        switch (direction) {
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
     *
     * @param direction Direction in which the snake should move
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return Returns the current direction of the snake
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Increases the length of the snake's body by 1
     * TODO: Implement a data structure that avoids creating new bodypart objects
     */
    public void increaseLength() {
        Bodypart lastElement = snakebody.get(snakebody.size() - 1);
        snakebody.add(new Bodypart(lastElement.getprevY(), lastElement.getprevX()));
    }

    /**
     * Adjusts the x and y values of each body part (except the head)
     * after the snake has been moved
     */
    private void adjustPosValues() {
        if (snakebody.size() > 1) {
            for (int i = 1; i < snakebody.size(); i++) {
                Bodypart predecessor = snakebody.get(i - 1);
                snakebody.get(i).registerPos(predecessor.getprevY(), predecessor.getprevX());
            }
        }
    }

    /**
     * @return Returns the list with the body parts of the snake
     */
    public List<Bodypart> getBodyparts() {
        return snakebody;
    }
}
