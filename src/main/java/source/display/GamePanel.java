package source.display;

import source.game.cards.Card;
import source.game.core.GameInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    final int xSize = 150;
    private final ArrayList<BufferedImage> handImages = new ArrayList<>();
    private final ArrayList<BufferedImage> boardImages = new ArrayList<>();

    public GamePanel() {
        this.setLayout(new GridLayout());
        this.setVisible(true);
        this.setBackground(Color.gray);
    }

    public void showGame(GameInfo info) {
        handImages.clear();
        boardImages.clear();

        for (Card c : info.getCurrentPlayer().getCards()) {
            handImages.add(ImageLoader.scale(ImageLoader.load(c), xSize));
        }

        for (Card c : info.getBoard().getCards()) {
            boardImages.add(ImageLoader.scale(ImageLoader.load(c), xSize));
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);


        for (int i = 0; i < handImages.size(); i++) {
            BufferedImage img = handImages.get(i);
            g2d.drawImage(img, i * xSize, 500, img.getWidth(), img.getHeight(), null);
        }

        for (int i = 0; i < boardImages.size(); i++) {
            BufferedImage img = boardImages.get(i);
            g2d.drawImage(img, i * xSize, 100, img.getWidth(), img.getHeight(), null);
        }

    }
}
