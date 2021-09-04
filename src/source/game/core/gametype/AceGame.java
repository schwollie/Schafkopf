package source.game.core.gametype;

import source.game.cards.Card;
import source.game.cards.Cardtype;
import source.game.cards.Color;
import source.game.core.Board;
import source.game.core.GameManager;
import source.game.core.rules.RuleSet;
import source.game.player.Player;

import java.util.ArrayList;

public class AceGame extends Gametype {

    public static final int value = 2;

    public Player player; // Player who plays the ace game
    public Color color; // color of the ace to play with

    public RuleSet ruleSet;

    public AceGame(Player p, Color c) {
        this.color = c;
        this.player = p;
    }

    @Override
    public int getVal() {
        return value;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public int getCardVal(Card card) {
        int trumpfVal = 0;

        // falls trumpf ohne ober unter
        if (card.getColor().equals(Color.Hearts)&&
                (!card.getType().equals(Cardtype.Ober)&&!card.getType().equals(Cardtype.Unter))) {
            trumpfVal = 50;
        }

        switch (card.getType()) {
            case Ober -> trumpfVal = 200;
            case Unter -> trumpfVal = 100;
        }

        return card.getValue()/4 + trumpfVal + card.getColor().value*4;
    }

    // checks if player is allowed to play this ace
    // false if player has own ace or
    // false if player has not the color of the played ace
    // false if color is heart
    public static boolean allowed(AceGame aceGame) {

        if (aceGame.color.equals(Color.Hearts)) {
            return false;
        }

        ArrayList<Card> pCards = aceGame.player.getCards();

        boolean hasSameColor = false;

        for (Card card : pCards) {
            if (card.getColor().equals(aceGame.color) && card.getType().equals(Cardtype.Ace)) {
                return false;
            } else if (card.getColor().equals(aceGame.color)) {
                hasSameColor = true;
            }
        }

        return hasSameColor;
    }

    @Override
    public boolean isTrumpf(Card c) {
        if (c.getType().equals(Cardtype.Unter) || c.getType().equals(Cardtype.Ober) || c.getColor().equals(Color.Hearts)) {
            return true;
        }
        return false;
    }

    @Override
    public int getWinner(Board board) {
        return 0;
    }

    // returns all cards that are possible to play
    @Override
    public ArrayList<Card> possibleCards(Player player, GameManager g) {

        Card first = g.getBoard().getFirst();
        boolean isTrumpf = false;

        ArrayList<Card> possCards = new ArrayList<>();

        if (first.equals(null)) {
            return player.getCards();
        }

        isTrumpf = this.isTrumpf(first);

        for (Card c : player.getCards()) {


            if (isTrumpf && this.isTrumpf(c)) {
                possCards.add(c);
            } else if (!isTrumpf && first.getColor().equals(c.getColor())) {
                possCards.add(c);
            }
        }

        return possCards.size() == 0 ?
    }
}
