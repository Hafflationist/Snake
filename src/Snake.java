import java.util.Random;
import java.util.List;
import java.util.LinkedList;
/**
 *
 */
public class Snake {
    private int _length;
    private GameBoard _gameBoard;
    private List<Bodypart> _snakebody;

    /**
     * Creates a Snake object
     */
    public Snake(GameBoard gameBoard)
    {
        _length = 1;
        _gameBoard = gameBoard;
        _snakebody = new LinkedList<Bodypart>();
        genStartPos();
    }

    /**
     * Randomly generates a starting position for the snake
     */
    private void genStartPos()
    {
        Random random = new Random();
        Bodypart head = new Bodypart(random.nextInt(_gameBoard.getBoardHeight()-1) + 1,
                random.nextInt(_gameBoard.getBoardWidth()-1)+1);
        _snakebody.add(head);
    }

    /**
     * Moves the snake one field forward on the game board
     */
    public void moveForward()
    {
        Bodypart head = _snakebody.get(0);
        if (head._prevY > head._currentY) {
            //direction: north
            head.registerPos(head._currentY-1,head._currentX);
        }
        else if (head._prevY < head._currentY)
        {
            //direction: south
            head.registerPos(head._currentY+1, head._currentX);
        }
        else if (head._prevX > head._currentX)
        {
            //direction: west
            head.registerPos(head._currentY, head._currentX-1);
        }
        else
        {
            //direction: east
            head.registerPos(head._currentY, head._currentX+1);
        }
        adjustSnakePos();
    }

    /**
     * Moves the snake one field to the left on the game board
     */
    public void turnLeft()
    {
        Bodypart head = _snakebody.get(0);
        if (head._prevY > head._currentY) {
            //direction: north
            head.registerPos(head._currentY, head._currentX - 1);
        }
        else if (head._prevY < head._currentY)
        {
            //direction: south
            head.registerPos(head._currentY, head._currentX + 1);
        }
        else if (head._prevX > head._currentX)
        {
            //direction: west
            head.registerPos(head._currentY + 1, head._currentX);
        }
        else
        {
            //direction: east
            head.registerPos(head._currentY - 1, head._currentX);
        }
        adjustSnakePos();
    }

    /**
     * Moves the snake one field to the right on the game board
     */
    public void turnRight()
    {
        Bodypart head = _snakebody.get(0);
        if (head._prevY > head._currentY) {
            //direction: north
            head.registerPos(head._currentY, head._currentX + 1);
        }
        else if (head._prevY < head._currentY)
        {
            //direction: south
            head.registerPos(head._currentY, head._currentX -1);
        }
        else if (head._prevX > head._currentX)
        {
            //direction: west
            head.registerPos(head._currentY - 1, head._currentX);
        }
        else
        {
            //direction: east
            head.registerPos(head._currentY + 1, head._currentX);
        }
        adjustSnakePos();
    }

    /**
     * Increases the length of the snake's body by 1
     */
    public void increaseLength()
    {
        Bodypart lastElement = _snakebody.get(_snakebody.size() - 1);
        _snakebody.add(new Bodypart(lastElement._prevY, lastElement._prevX));
    }

    /**
     * Adjusts the position values of each bodypart
     */
    private void adjustSnakePos()
    {
        if(_snakebody.size() > 1) {
            for (int i = 1; i < _snakebody.size(); i++) {
                Bodypart predecessor = _snakebody.get(i - 1);
                _snakebody.get(i).registerPos(predecessor._prevY, predecessor._prevX);
            }
        }
        //TODO: Method nees to be moved to gamelogic class
        for(Bodypart bodypart : _snakebody)
        {
            _gameBoard.resetField(bodypart._prevY, bodypart._prevX);
            _gameBoard.markPosition(bodypart._currentY, bodypart._currentX);
        }
    }


    /**
     * @return the current length of the Snake
     */
    public int getLength()
    {
        return _length;
    }



    /**
     * this class is being used to track the current and previous position
     * of each body part of the snake. It is being used to correctly calculate
     * the position of the snake on the game board
     */
    private class Bodypart {
        private int _currentX;
        private int _currentY;
        private int _prevX;
        private int _prevY;

        /**
         * Creates a new bodypart object
         * @param posY the Y value of the bodypart on the board
         * @param posX the X value of the bodypart on the board
         */
        Bodypart(int posY, int posX)
        {
            _currentY = posY;
            _currentX = posX;
            _prevY = _currentY;
            _prevX = _currentX + 1;
        }

        /**
         * Registers the position of the bodypart
         * @param posY the y value of the new position
         * @param posX the x value of the new position
         */
        public void registerPos(int posY, int posX)
        {
            _prevY = _currentY;
            _prevX = _currentX;
            _currentY = posY;
            _currentX = posX;
        }

        /**
         *
         * @return Returns the current Y value of the body part position
         */
        public int getY()
        {
            return _currentY;
        }

        /**
         *
         * @return Returns the current X value of the body part position
         */
        public int getX()
        {
            return _currentX;
        }

        /**
         *
         * @return Returns the previous Y value of the body part position
         */
        public int getprevY()
        {
            return _prevY;
        }

        /**
         *
         * @return Returns the previous X value of the body part position
         */
        public int getprevX()
        {
            return _prevX;
        }
    }
}
