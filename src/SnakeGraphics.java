import javax.swing.*;
import java.awt.*;

public class SnakeGraphics extends JPanel {

    public SnakeGraphics()
    {

    }

    @Override
    public void paintComponents(Graphics g)
    {
        super.paintComponents(g);
        setBackground(Color.BLACK);
        drawObjects(g);
    }


    public void drawObjects(Graphics g)
    {

    }

}
