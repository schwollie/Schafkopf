package source.game.core.rules;

import source.game.cards.Card;
import source.game.core.board.Board;
import source.game.core.gametype.Gametype;
import source.game.player.Player;

import java.util.ArrayList;

public class Rulset {

    public final ArrayList<Rule> rules = new ArrayList<>();
    private final Gametype gametype;
    private boolean col2col = false;
    private boolean excludeTrunpf = true; // col2col only with trumpf
    private boolean trumpf2trumpf = false;

    public Rulset(Gametype gametype) {
        this.gametype = gametype;
    }

    public ArrayList<Card> getPossibleCards(Player p, Board b) {
        Card first = b.getFirst();

        ArrayList<Card> pCards = new ArrayList<>(p.getCards());
        pCards = applyCol2Col(first, pCards);
        pCards = applyTrumpf2Trumpf(first, pCards);

        for (Rule r : rules) {
            pCards = r.applyRule(first, pCards);
        }

        return pCards;
    }

    private ArrayList<Card> applyCol2Col(Card first, ArrayList<Card> pCards) {
        if (!col2col) {
            return pCards;
        }
        if (gametype.isTrumpf(first) && excludeTrunpf) {
            return pCards;
        }
        ArrayList<Card> newCards = new ArrayList<>();

        for (Card c : pCards) {
            if (c.getColor().equals(first.getColor())) {
                if (!excludeTrunpf == gametype.isTrumpf(c) || !excludeTrunpf) {
                    newCards.add(c);
                }
            }
        }

        if (newCards.size() == 0) {
            return pCards;
        }

        return newCards;
    }

    private ArrayList<Card> applyTrumpf2Trumpf(Card first, ArrayList<Card> pCards) {
        if (!trumpf2trumpf || !gametype.isTrumpf(first)) {
            return pCards;
        }
        ArrayList<Card> newCards = new ArrayList<>();

        for (Card c : pCards) {
            if (gametype.isTrumpf(c)) {
                newCards.add(c);
            }
        }

        if (newCards.size() == 0) {
            return pCards;
        }

        return newCards;
    }

    public void setCol2col(boolean col2col) {
        this.col2col = col2col;
    }

    public void setExcludeTrunpf(boolean excludeTrunpf) {
        this.excludeTrunpf = excludeTrunpf;
    }

    public void setTrumpf2trumpf(boolean trumpf2trumpf) {
        this.trumpf2trumpf = trumpf2trumpf;
    }

    public void addRule(Rule r) {
        this.rules.add(r);
    }
}
