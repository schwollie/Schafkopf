package source.ai;

import source.game.cards.Card;
import source.game.cards.Cardtype;
import source.game.cards.Color;
import source.game.core.GameInfo;
import source.game.core.GameManager;
import source.game.core.gametype.AceGame;
import source.game.player.Player;
import source.game.stack.Stack;

import java.util.ArrayList;

public class InputGenerator {

    //
    public static double[] generateInput(GameInfo info, Player p) {
        double[] input = new double[37];

        Stack stack = new Stack();
        stack.createDefaultStack();
        ArrayList<Card> cards = Card.sort(stack.getCards(), new AceGame(p, Color.Acorns));

        Player[] players = info.getPlayersForGameOrdered();

        // layed cards:

        for (int pl = 0; pl < players.length; pl++) {
            for (int i = 0; i < 8; i++) {
                Card c = i >= players[pl].getCardsLayed().size() ? null : players[pl].getCardsLayed().get(i);
                input[pl*8+i] = getCardVal(cards, c);
            }
        }

        int add = 32;
        // cards on board
        for (int i = 0; i<4;i++) {
            Card c = i >= info.getBoard().getCards().size() ? null : info.getBoard().getCards().get(i);
            input[add+i] = getCardVal(cards, c);
            i++;
        }

        add = 36;
        // spielsau
        input[add] = info.getGametype().getColor().value / 4.0;

        return input;
    }

    private static double getCardVal(ArrayList<Card> sorted, Card c) {
        if (c==null) { return -1.0; }
        return sorted.indexOf(c)/32.0;
    }

    // input contains layed Cards (32), cards on board (4*32), cards on hand(32) = 192 total
    public static double[] generateInputOld(GameInfo info, Player p) {
        double[] input = new double[192];

        Stack stack = new Stack();
        stack.createDefaultStack();

        ArrayList<Card> cards = stack.getCards();

        int i = 0;
        for (Card c: cards) {
            if (info.getLayedCards().contains(c)) {
                input[i] = 1;
            }
            i++;
        }

        int b = 0;
        for (Card c : info.getBoard().getCards()) {
            input[cards.indexOf(c)+i+32*b] = 1;
            b++;
        }

        i = 160;
        for (Card c : p.getCards()) {
            input[cards.indexOf(c)+i] = 1;
        }

        return input;
    }

    public static void main(String[] args) {
        //InputGenerator.generateInput(new GameManager(0), );
    }

}
