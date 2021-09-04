package source.game.core;

import source.game.cards.Card;

import java.util.ArrayList;

public class Board {

    ArrayList<Card> cards = new ArrayList<>();

    public void clear() {
        cards.clear();
    }

    public Card getFirst() {
        if (cards.size()==0) { return null;}
        return cards.get(0);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void layCard(Card c) {
        cards.add(c);
        System.out.println(cards.size());
    }
}
