package source.game.core.board;

import source.Settings;
import source.game.cards.Card;
import source.game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    final HashMap<Card, Player> players = new HashMap<>();
    final ArrayList<Card> cards = new ArrayList<>();

    public void clear() {
        if (Settings.verbose>1) {
            System.out.println("board: " + this.cards);
        }
        cards.clear();
        players.clear();
    }

    public Card getFirst() {
        if (cards.size() == 0) {
            return null;
        }
        return cards.get(0);
    }

    public Player getPlayerFromCard(Card c) {
        return players.get(c);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void layCard(Card c, Player p) {
        p.getGame().getInfo().getLayedCards().add(c);
        players.put(c, p);
        cards.add(c);
        //System.out.println(cards.size());
    }
}
