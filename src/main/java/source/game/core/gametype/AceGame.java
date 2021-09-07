package source.game.core.gametype;

import source.game.cards.Card;
import source.game.cards.Cardtype;
import source.game.cards.Color;
import source.game.core.GameManager;
import source.game.core.board.Board;
import source.game.core.rules.Rule;
import source.game.core.rules.Rulset;
import source.game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class AceGame extends Gametype {

    public static final int value = 2;

    public final Player player; // Player who plays the ace game
    public final Player player2; // Player who has the ace
    public final Color color; // color of the ace to play with

    public Rulset ruleSet;

    public AceGame(Player p, Color c) {
        this.color = c;
        this.player = p;
        createRuleset();
        this.player2 = getPlayer2();
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

    private Player getPlayer2() {
        Player[] players = this.player.getGame().getInfo().getPlayers();
        for (Player p : players) {
            if (p.hasCard(Cardtype.Ace, this.color) != -1) {
                return p;
            }
        }
        return null;
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

        if (isTrumpf(card)) {
            trumpfVal = 1000;
        }

        switch (card.getType()) {
            case Ober -> trumpfVal += 200;
            case Unter -> trumpfVal += 100;
        }

        return card.getValue() / 4 + trumpfVal + card.getColor().value * 4;
    }

    private void createRuleset() {
        Rulset r = new Rulset(this);
        r.setCol2col(true);
        r.setExcludeTrunpf(true);
        r.setTrumpf2trumpf(true);

        ArrayList<Card> from = Rule.getAllCards(this, this.color, false);
        ArrayList<Card> to = new ArrayList<>(Arrays.stream(new Card[]{new Card(Cardtype.Ace, this.color)}).toList());
        Rule rule = new Rule(this, from, to);

        r.addRule(rule);

        this.ruleSet = r;
    }

    @Override
    public boolean isTrumpf(Card c) {
        return c.getType().equals(Cardtype.Unter) || c.getType().equals(Cardtype.Ober) || c.getColor().equals(Color.Hearts);
    }

    @Override
    public Player getStichWinner(Board board) {
        ArrayList<Card> cards = Card.sort(board.getCards(), this);

        if (isTrumpf(cards.get(3))) {
            return board.getPlayerFromCard(cards.get(3));
        }

        for (int i = 3; i >= 0; i--) {
            if (board.getFirst().getColor().equals(cards.get(i).getColor())) {
                return board.getPlayerFromCard(cards.get(i));
            }
        }

        throw new Error("must not reach this point");
    }

    @Override
    public Player[] getGameWinner(ArrayList<Player> players) {
        ArrayList<Player> winners = new ArrayList<>(players);

        int playerPoints = 0;

        for (Player p : players) {
            if (p == this.player || p == this.player2) {
                playerPoints += p.getPoints();
            }
        }

        if (playerPoints > 61) {
            return new Player[]{player, player2};
        }

        winners.remove(player2);
        winners.remove(player);

        return winners.toArray(new Player[]{});
    }

    // returns all cards that are possible to play
    @Override
    public ArrayList<Card> possibleCards(Player player, GameManager g) {
        if (g.getBoard().getFirst() == null) {
            return player.getCards();
        }
        return ruleSet.getPossibleCards(player, g.getBoard());
    }
}
