public class Gamelogic {

    private Snake _snake;
    private GameBoard _gameBoard;
    private UI _userinterface;

    /**
     * Creates a new Gamelogic object
     */
    public Gamelogic()
    {
        _gameBoard = new GameBoard(20,20);
        _snake = new Snake(_gameBoard.getBoardHeight(), _gameBoard.getBoardWidth());
        _userinterface = new UI();
    }

    /**
     * Method used to run the game
     */
    public void run()
    {

    }

}

/*
        for (Bodypart bodypart : _snakebody) {
            _gameBoard.resetField(bodypart.getprevY(), bodypart.getprevX());
            _gameBoard.markPosition(bodypart.getY(), bodypart.getX());
        }
 */