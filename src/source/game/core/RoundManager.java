package source.game.core;

import source.game.core.gametype.Gametype;
import source.game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class RoundManager {

    GameManager game;

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
        Player start = game.info.getLastWinnner();
        int playerIndex = Arrays.stream(game.players).toList().indexOf(start);

        game.getBoard().clear();

        for (int i = 0; i<4; i++) {
            game.info.setCurrentPlayer(game.players[playerIndex]);
            game.sendChangeInfo();
            game.players[playerIndex].playRound(game);
            playerIndex = (playerIndex+1) % 4;
        }

        game.info.increaseRound();
    }

}
