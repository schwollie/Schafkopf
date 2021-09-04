package source.game.core;

import source.game.GameChangeListener;
import source.game.core.gametype.AceGame;
import source.game.player.HumanPlayer;
import source.game.player.Player;
import source.game.player.RandomPlayer;
import source.game.stack.Stack;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {

    Player[] players = new Player[4];
    Stack stack = new Stack();
    GameInfo info;
    Board board = new Board();
    public ArrayList<GameChangeListener> gameChangeListeners = new ArrayList<>();

    public GameManager(int humanPlayers) {
        init(humanPlayers);
        info = new GameInfo(this);
    }

    void init(int humans) {
        stack.init();
        createPlayers(humans);
    }

    public void startGame() {
        RoundManager r = new RoundManager(this);
        info.setGametype(r.decideGameType());

        logGameType();

        sendChangeInfo();

        // each game has 8 rounds
        for (int i = 0; i < 8; i++) {
            r.nextRound();
            board.clear();
        }
    }

    void createPlayers(int num) {
        for (int i = 0; i<4; i++) {
            if (i < num) {
                players[i] = new HumanPlayer(this);
            } else {
                players[i] = new RandomPlayer(this);
            }

            stack.givePlayerCards(players[i]);
        }
    }

    public Board getBoard() {
        return board;
    }

    public void sendChangeInfo() {
        for (GameChangeListener changeListener : gameChangeListeners) {
            changeListener.onStatusChange(this.info);
        }
    }

    public void listen(GameChangeListener listener) {
        this.gameChangeListeners.add(listener);
    }

    public void stopListening(GameChangeListener listener) {
        this.gameChangeListeners.remove(listener);
    }

    public GameInfo getInfo() {
        return info;
    }

    public void logGameType() {
        if (info.getGametype() instanceof AceGame) {
            AceGame g = ((AceGame) info.getGametype());
            Player p = g.player;
            int index = Arrays.asList(players).indexOf(p);

            System.out.println("Player " + index + " plays with the " + g.color + " Ace");
        } else {
            System.out.println("Ramsch?");
        }
    }
}
