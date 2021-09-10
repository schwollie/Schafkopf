package source.game.core;

import source.Settings;
import source.game.core.gametype.Gametype;
import source.game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class RoundManager {

    final GameManager game;

    public RoundManager(GameManager game) {
        this.game = game;
    }

    Gametype decideGameType() {
        ArrayList<Gametype> gametypes = new ArrayList<>();

        for (Player p : game.players) {
            game.getInfo().setCurrentPlayer(p);
            game.sendChangeInfo();
            gametypes.add(p.selectGameType());
        }

        return Gametype.choseType(gametypes);
    }

    void nextRound() {
        game.getBoard().clear();

        for (Player currentPlayer : game.info.getPlayersForGameOrdered()) {
            game.info.setCurrentPlayer(currentPlayer);
            game.sendChangeInfo();

            currentPlayer.playRound(game);

            game.sendChangeInfo();


            //try {
            //    Thread.sleep(2000);
            //} catch (InterruptedException e) {}


        }

        Player winner = game.info.gametype.getStichWinner(game.board);
        winner.addStich(game.board);
        game.getInfo().setLastWin(winner);

        if (Settings.verbose > 1) {
            System.out.println("Round winner is " + winner);
        }


        //try {
        //    Thread.sleep(2000);
        //} catch (InterruptedException e) {}


        game.info.increaseRound();
    }

}
