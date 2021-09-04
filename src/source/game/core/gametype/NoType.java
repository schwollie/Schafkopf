package source.game.core.gametype;

import source.game.cards.Card;
import source.game.cards.Color;
import source.game.core.Board;
import source.game.core.GameManager;
import source.game.player.Player;

import java.util.ArrayList;

public class NoType extends Gametype{

    public static final int value = 1;

    @Override
    public int getVal() {
        return value;
    }

    @Override
    public Color getColor() {
        return Color.Acorns;
    }

    @Override
    public int getCardVal(Card card) {
        return card.getValue();
    }

    @Override
    public int getWinner(Board board) {
        return 0;
    }

    @Override
    public ArrayList<Card> possibleCards(Player player, GameManager g) {
        return null;
    }
}
