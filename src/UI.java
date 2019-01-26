import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class UI implements ActionListener{

    private JFrame _mainWindow;
    private Gamelogic _gamelogic;
    private Timer _timer;
    private static final int DELAY = 500;

    public UI(Gamelogic gamelogic)
    {
        _gamelogic = gamelogic;
        _timer = new Timer(DELAY,this);
        _mainWindow = new JFrame("Snake");
        _mainWindow.setSize(200,200);
        _mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _mainWindow.setResizable(false);
        _mainWindow.setLocationRelativeTo(null);
        _mainWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                _gamelogic.turnSnake(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        _mainWindow.setVisible(true);
        _timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _gamelogic.move();
    }
}
