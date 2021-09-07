package source.display;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final GamePanel gamePanel = new GamePanel();

    public Window(int w, int h) {
        this.setSize(new Dimension(w, h));
        this.setTitle("Schafkopf");
        this.setFocusable(true);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout());

        this.add(gamePanel);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
