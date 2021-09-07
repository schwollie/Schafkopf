package source.display;

import source.game.GameChangeListener;
import source.game.core.GameInfo;
import source.game.core.GameManager;

import java.awt.*;

public class VisualManager implements GameChangeListener {

    GameManager game;
    Window window;

    public void start(GameManager game) {
        this.game = game;
        game.listen(this);
        window = new Window(1200, 800);
    }

    @Override
    public void onStatusChange(GameInfo info) {
        window.getGamePanel().showGame(info);
        EventQueue.invokeLater(window::repaint);
    }
}
