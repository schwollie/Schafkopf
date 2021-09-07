package source.game.core;

import source.game.cards.Card;
import source.game.core.board.Board;
import source.game.core.gametype.Gametype;
import source.game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class GameInfo {

    final GameManager game;

    Gametype gametype;

    Player lastWin;
    Player currentPlayer;

    ArrayList<Card> layedCards = new ArrayList<>();

    private int round;

    public GameInfo(GameManager g) {
        lastWin = g.players[0];
        this.game = g;
    }

    // returns the players ordered so that the last winner is the first one ...
    public Player[] getPlayersForGameOrdered() {
        int playerIndex = Arrays.stream(game.players).toList().indexOf(lastWin);
        Player[] po = new Player[4];

        for (int i = 0; i<4; i++) {
            po[i] = game.players[(playerIndex + i) % 4];
        }

        return po;
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

    public ArrayList<Card> getLayedCards() {
        return layedCards;
    }
}
