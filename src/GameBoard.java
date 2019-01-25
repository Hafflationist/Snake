/**
 *
 */
public class GameBoard {

    private int[][] _board;
    private int _boardWidth;
    private int _boardHeight;

    /**
     * Creates a new GameBoard Object
     * @param boardHeight desired height of the GameBoard
     * @param boardWidth desired width of the GameBoard
     */
    public GameBoard(int boardHeight, int boardWidth)
    {
        _boardWidth = boardWidth + 2;
        _boardHeight = boardHeight + 2;
        _board = new int[_boardHeight][_boardWidth];
        initBoard();
    }

    /**
     * Initializes the gameboard variables
     */
    private void initBoard()
    {
        for (int i = 1; i < _boardHeight - 1; i++)
        {
            for (int j = 1; j < _boardWidth - 1; j++)
            {
                _board[i][j] = 1;
            }
        }
    }

    /**
     * Sets a snack to the desired position. A snack is marked by a value of 2
     * @param posY x-position of the snack
     * @param posX y-position of the snack
     */
    public void setSnack(int posY, int posX)
    {
        if (posY >= 0 && posY < _boardHeight && posX >= 0 && posX < _boardWidth)
        {
            _board[posY][posX] = 2;
        }
        else
        {
            throw new IllegalArgumentException("Index out of bounds!");
        }
    }

    /**
     * TODO: Implement method
     * Sets the position of the snake on the game board
     * @param snake Snake object
     */
    public void markPosition(Snake snake)
    {
    }

    /**
     * Resets the board values on the designated field to 1
     * @param posY x-position of the field
     * @param posX y-position of the field
     */
    public void resetField(int posY, int posX)
    {
        if (posY > 0 && posY < _boardHeight-1 && posX > 0 && posX < _boardWidth-1)
        {
            _board[posY][posX] = 1;
        }
        else
        {
            throw new IllegalArgumentException("Index out of bounds!");
        }
    }

    /**
     * Checks, if the snake is on a field with a snack
     * @param posY x-position of the field
     * @param posX y-position of the field
     */
    public boolean isOnSnack(int posY, int posX)
    {
        if (posY > 0 && posY < _boardHeight-1 && posX > 0 && posX < _boardWidth-1)
        {
            if (_board[posY][posX] == 2)
            {
                _board[posY][posX] = 1;
                return true;
            }
            return false;
        }
        else
        {
            throw new IllegalArgumentException("Index out of bounds!");
        }
    }

    /**
     * Checks if the next field in the moving direction is clear
     * @param posY x-position of the field
     * @param posX y-position of the field
     */
    public boolean fieldClear(int posY, int posX)
    {
        if (posY >= 0 && posY < _boardHeight && posX >= 0 && posX < _boardWidth)
        {
            if (_board[posY][posX] == 0)
            {
                return false;
            }
            return true;
        }
        else
        {
            throw new IllegalArgumentException("Index out of bounds!");
        }
    }

    /**
     *
     * @return Returns the height of the playing area
     */
    public int getBoardHeight()
    {

        return _boardHeight - 2;
    }

    /**
     *
     * @return Returns the width of the playing area
     */
    public int getBoardWidth()
    {
        return _boardWidth - 2;
    }

    /**
     * For debug purposes only
     * Prints the values of the gameboard
     */
    public void printBoard()
    {
        for (int i = 0; i < _boardHeight; i++)
        {
            for (int j = 0; j < _boardWidth; j++)
            {
                System.out.print(_board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
