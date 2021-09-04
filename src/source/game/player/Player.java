package source.game.player;

import source.game.cards.Color;
import source.game.core.Board;
import source.game.core.GameManager;
import source.game.cards.Card;
import source.game.core.gametype.Gametype;
import source.game.core.gametype.NoType;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Card> stiche = new ArrayList<>();
    GameManager game;

    public Player(GameManager game) {
        this.game = game;
    }

    public void playRound(GameManager g) {
        System.out.println("play round");
    }

    public Gametype selectGameType() {
        if (Gametype.isAllowed(new NoType())) {
            return new NoType();
        }
        return null;
    }

    protected void layCard(Card c) {
        game.getBoard().layCard(c);
        this.cards.remove(c);
    }

    public void setCards(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public ArrayList<Card> getCards() {
        return Card.sort(cards, game.getInfo().getGametype());
    }

    public void addStich(Board b) {
        stiche.addAll(b.getCards());
    }

    public ArrayList<Card> getStiche() {
        return stiche;
    }

    public int getCardCount(Color c) {
        int i = 0;
        for (Card card : cards) {
            if (card.getColor().equals(c)) {
                i++;
            }
        }
        return i;
    }
}
