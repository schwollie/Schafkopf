package source.display;

import source.game.cards.Card;
import source.game.cards.Cardtype;
import source.game.cards.Color;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class ImageLoader {

    public static final HashMap<String, BufferedImage> imgs = new HashMap<>();
    public static boolean loaded = false;

    public static void loadAll() {
        for (Cardtype c : Cardtype.values()) {
            for (Color col : Color.values()) {
                BufferedImage img = null;
                String path = "images/" + col + "/" + c + ".jpg";
                try {
                    img = ImageIO.read(new File(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgs.put(path, img);
            }
        }

        loaded = true;
    }

    public static BufferedImage load(Card card) {
        if (!loaded) {
            loadAll();
        }
        String path = "images/" + card.getColor() + "/" + card.getType() + ".jpg";
        return imgs.get(path);
    }

    public static BufferedImage scale(BufferedImage before, double dw, double dh) {
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(dw, dh);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(before, after);
        return after;
    }

    public static BufferedImage scale(BufferedImage before, int pixelW) {
        int w = before.getWidth();
        int h = before.getHeight();
        double scale = (double) pixelW / w;
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        after = scaleOp.filter(before, after);
        return after;
    }

}
