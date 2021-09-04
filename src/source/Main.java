package source;

import source.display.VisualManager;
import source.game.core.GameManager;

public class Main {

    public static void main(String[] args) {
        GameManager g = new GameManager(1);
        VisualManager v = new VisualManager();

        v.start(g);
        g.startGame();
    }
}
