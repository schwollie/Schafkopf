package source.game.core;

import source.game.core.board.Board;
import source.game.core.gametype.Gametype;
import source.game.player.Player;

public class GameInfo {

    GameManager game;

    Gametype gametype;

    Player lastWin;
    Player currentPlayer;

    private int round;

    public GameInfo(GameManager g) {
        lastWin = g.players[0];
        this.game = g;
    }

    public Player getLastWinnner() {
        return lastWin;
    }

    public void setLastWin(Player lastWin) {
        this.lastWin = lastWin;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Board getBoard() {
        return game.getBoard();
    }

    public Gametype getGametype() {
        return gametype;
    }

    public void setGametype(Gametype gametype) {
        this.gametype = gametype;
    }

    public void increaseRound() {
        round++;
    }

    public Player[] getPlayers() {
        return game.players;
    }
}
