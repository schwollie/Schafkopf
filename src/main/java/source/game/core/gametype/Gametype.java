package source.game.core.gametype;

import source.game.cards.Card;
import source.game.cards.Color;
import source.game.core.GameManager;
import source.game.core.board.Board;
import source.game.player.Player;

import java.util.ArrayList;

public abstract class Gametype {

    public static Gametype choseType(ArrayList<Gametype> gametypes) {
        Gametype highest = new NoType();

        for (Gametype t : gametypes) {
            if (t.getVal() > highest.getVal()) {
                highest = t;
                continue;
            }
            if (t.getVal() == highest.getVal() && t.getColor().value > highest.getColor().value) {
                highest = t;
            }
        }

        return highest;
    }

    public static boolean isAllowed(Gametype gametype) {
        if (gametype instanceof AceGame) {
            return AceGame.allowed((AceGame) gametype);
        } else return gametype instanceof NoType;
    }

    public static ArrayList<Gametype> getPossibleTypes(Player p) {
        ArrayList<Gametype> gametypes = new ArrayList<>();
        for (Color c : Color.values()) {
            if (AceGame.allowed(new AceGame(p, c))) {
                gametypes.add(new AceGame(p, c));
            }
        }

        gametypes.add(new NoType());
        return gametypes;
    }

    public abstract int getVal();

    public abstract Color getColor();

    public abstract int getCardVal(Card card);

    public boolean isTrumpf(Card c) {
        return false;
    }

    // returns index of winner card
    public abstract Player getStichWinner(Board board);

    public abstract Player[] getGameWinner(ArrayList<Player> players);

    public abstract ArrayList<Card> possibleCards(Player player, GameManager g);

}
