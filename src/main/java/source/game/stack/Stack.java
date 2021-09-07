package source.game.stack;

import source.game.cards.Card;
import source.game.cards.Cardtype;
import source.game.cards.Color;
import source.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Stack {

    final ArrayList<Card> cards = new ArrayList<>();

    public void init() {
        this.createDefaultStack();
        this.shuffle();
    }

    public void createDefaultStack() {
        for (Cardtype c : Cardtype.values()) {
            for (Color col : Color.values()) {
                cards.add(new Card(c, col));
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i < 1000; i++) {
            Collections.shuffle(cards);
        }
    }

    public void givePlayerCards(Player p) {
        p.setCards(cards.subList(0, 8));
        cards.removeAll(cards.subList(0, 8));
    }

    @Override
    public String toString() {
        return "Stack{" +
                "cards=" + cards +
                '}';
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
