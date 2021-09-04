package source.game.player;

import source.game.cards.Card;
import source.game.core.GameManager;
import source.game.core.gametype.Gametype;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player{

    private Random rnd = new Random();

    public RandomPlayer(GameManager game) {
        super(game);
    }

    @Override
    public Gametype selectGameType() {
        ArrayList<Gametype> gametypes = Gametype.getPossibleTypes(this);
        int rndIndex = rnd.nextInt(gametypes.size());

        System.out.println("select game = " + gametypes.get(rndIndex).getClass());
        return gametypes.get(rndIndex);
    }

    @Override
    public void playRound(GameManager g) {
        ArrayList<Card> pcards = game.getInfo().getGametype().possibleCards(this, game);

        this.layCard(pcards.get(rnd.nextInt(pcards.size())));
    }
}
