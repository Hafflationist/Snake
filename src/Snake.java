import java.util.Random;
/**
 *
 */
public class Snake {
    private int _length;
    private GameBoard _gameBoard;
    private Bodypart _head;

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
        _head = new Bodypart(random.nextInt(_gameBoard.getBoardHeight()-1) + 1,
                random.nextInt(_gameBoard.getBoardWidth()-1)+1);
    }

    /**
     * Moves the snake one field forward on the game board
     */
    public void moveForward()
    {

    }

    /**
     * Moves the snake one field to the left on the game board
     */
    public void turnLeft()
    {

    }

    /**
     * Moves the snake one field to the right on the game board
     */
    public void turnRight()
    {

    }

    /**
     * Adjusts the position values of each bodypart
     */
    public void adjustSnakePos()
    {

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
            _prevX = _currentX;
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
