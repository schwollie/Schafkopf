package source.game.player;

import source.Settings;
import source.game.cards.Card;
import source.game.cards.Cardtype;
import source.game.cards.Color;
import source.game.core.GameManager;
import source.game.core.board.Board;
import source.game.core.gametype.Gametype;
import source.game.core.gametype.NoType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Player {

    final ArrayList<Card> cardsLayed = new ArrayList<>();
    final ArrayList<Card> stiche = new ArrayList<>();
    final GameManager game;
    private final String name;
    ArrayList<Card> cards = new ArrayList<>();

    public Player(GameManager game, String name) {
        this.game = game;
        this.name = name;
    }

    public void playRound(GameManager g) {
        if (Settings.verbose > 1) {System.out.println(this + " plays now");}
    }

    public Gametype selectGameType() {
        Random rnd = new Random();
        ArrayList<Gametype> gametypes = Gametype.getPossibleTypes(this);

        if (gametypes.size()>1) {
            gametypes.remove(gametypes.size()-1);// last one is notype
        }
        int rndIndex = rnd.nextInt(gametypes.size());
        return gametypes.get(rndIndex);
    }

    protected void layCard(Card c) {
        game.getBoard().layCard(c, this);
        cardsLayed.add(c);
        this.cards.remove(c);
    }

    public ArrayList<Card> getCards() {
        return Card.sort(cards, game.getInfo().getGametype());
    }

    public void setCards(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public void addStich(Board b) {
        stiche.addAll(b.getCards());
    }

    public ArrayList<Card> getStiche() {
        return stiche;
    }

    public int getPoints() {
        int val = 0;
        for (Card c : stiche) {
            val += c.getValue();
        }
        return val;
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

    public int hasCard(Cardtype cardtype, Color col) {
        int i = 0;
        for (Card c : cards) {
            if (c.getColor().equals(col) && c.getType().equals(cardtype)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public GameManager getGame() {
        return game;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ArrayList<Card> getCardsLayed() {
        return cardsLayed;
    }
}
