package source.game.core.board;

import source.game.cards.Card;
import source.game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    HashMap<Card, Player> players = new HashMap<>();
    ArrayList<Card> cards = new ArrayList<>();

    public void clear() {
        cards.clear();
        players.clear();
    }

    public Card getFirst() {
        if (cards.size()==0) { return null;}
        return cards.get(0);
    }

    public Player getPlayerFromCard(Card c) {
        return players.get(c);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void layCard(Card c, Player p) {
        players.put(c, p);
        cards.add(c);
        System.out.println(cards.size());
    }
}
