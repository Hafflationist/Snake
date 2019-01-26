

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(10,10);
        Snake snake = new Snake(gameBoard);
        gameBoard.printBoard();
    }
}
