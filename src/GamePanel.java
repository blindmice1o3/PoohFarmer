import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    Game game;

    public GamePanel(Game game) {
        this.game = game;
    } // **** end GamePanel() constructor ****

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

} // **** end GamePanel class ****