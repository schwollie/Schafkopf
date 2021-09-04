package source.game.cards;

import source.game.core.gametype.Gametype;
import source.game.core.gametype.NoType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Card implements Comparator<Card> {

    int value;
    Cardtype type;
    Color color;

    Gametype gametype;

    public Card(Cardtype c, Color color) {
        this.type = c;
        this.value = c.value;
        this.color = color;
        this.gametype = new NoType();
    }

    public Card(Cardtype c, Color color, Gametype gametype) {
        this.type = c;
        this.value = c.value;
        this.color = color;
        this.gametype = gametype == null ? new NoType() : gametype;
    }

    @Override
    public String toString() {
        return "(" + this.type + ", " + this.color + ")";
    }

    public Cardtype getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public static ArrayList<Card> sort(ArrayList<Card> cards) {
        cards.sort(new Card(Cardtype.Ace, Color.Acorns));
        return cards;
    }

    public static ArrayList<Card> sort(ArrayList<Card> cards, Gametype gametype) {
        cards.sort(new Card(Cardtype.Ace, Color.Acorns, gametype));
        return cards;
    }

    public int compare(Card obj1, Card obj2) {
        int p1 = gametype.getCardVal(obj1);
        int p2 = gametype.getCardVal(obj2);

        if (p1 > p2) {
            return 1;
        } else if (p1 < p2){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) { return false; }
        return ((Card)obj).getColor().equals(this.color) && ((Card)obj).getType().equals(this.type);
    }
}
