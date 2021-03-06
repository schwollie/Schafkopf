package source.game.core.rules;

import source.game.cards.Card;
import source.game.cards.Cardtype;
import source.game.cards.Color;
import source.game.core.gametype.Gametype;

import java.util.ArrayList;

public class Rule {

    final ArrayList<Card> from;
    final ArrayList<Card> to;
    private final Gametype gametype;

    public Rule(Gametype gametype, ArrayList<Card> from, ArrayList<Card> to) {
        this.gametype = gametype;
        this.from = from;
        this.to = to;
    }

    public static ArrayList<Card> getAllCards(Gametype t, Color c, boolean includeTrumpf) {
        ArrayList<Card> cards = new ArrayList<>();

        for (Cardtype cardtype : Cardtype.values()) {
            Card nC = new Card(cardtype, c);
            if (!includeTrumpf && t.isTrumpf(nC)) {
                continue;
            }
            cards.add(nC);
        }

        return cards;
    }

    public ArrayList<Card> applyRule(Card first, ArrayList<Card> cards) {
        ArrayList<Card> newCards = new ArrayList<>();

        if (cardInList(first, from)) {
            for (Card c : cards) {
                if (cardInList(c, to)) {
                    newCards.add(c);
                }
            }
        }

        if (newCards.size() == 0) {
            return cards;
        }
        return newCards;

    }

    private boolean cardInList(Card c, ArrayList<Card> cards) {
        for (Card card : cards) {
            if (c.getColor().equals(card.getColor()) && c.getType().equals(card.getType())) {
                return true;
            }
        }
        return false;
    }
}
