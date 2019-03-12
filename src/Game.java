import javax.swing.*;
import java.awt.*;

public class Game {

    JFrame frame;
    JPanel panel;

    public Game() {
        init();
    } // **** end Game() constructor

    public void init() {
        frame = new JFrame("Pooh Farmer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new GamePanel(this);
        frame.setContentPane(panel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }

} // **** end Game class ****