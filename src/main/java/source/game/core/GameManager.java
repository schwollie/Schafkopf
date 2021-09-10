package source.game.core;

import source.Settings;
import source.game.GameChangeListener;
import source.game.core.board.Board;
import source.game.core.gametype.AceGame;
import source.game.core.gametype.Gametype;
import source.game.core.gametype.NoType;
import source.game.player.*;
import source.game.stack.Stack;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {

    public final ArrayList<GameChangeListener> gameChangeListeners = new ArrayList<>();
    final Player[] players = new Player[4];
    final Stack stack = new Stack();
    GameInfo info;
    final Board board = new Board();
    RoundManager r = new RoundManager(this);

    int humanPlayers, aiPlayers, trainPlayers;

    public GameManager(int humanPlayers, int aiPlayers, int trainPlayers) {
        this.humanPlayers = humanPlayers;
        this.aiPlayers = aiPlayers;
        this.trainPlayers = trainPlayers;
        init(humanPlayers, aiPlayers, trainPlayers);
    }

    private void init(int humans, int ais, int train) {
        stack.init();
        createPlayers(humans, ais, train);
        this.info = new GameInfo(this);
    }

    public Gametype decideGameType() {
        info.setGametype(r.decideGameType());
        while (info.getGametype() instanceof NoType) { // notype does not work
            init(humanPlayers, aiPlayers, trainPlayers);
            info.setGametype(r.decideGameType());
        }
        return info.getGametype();
    }

    public void startGame() {
        if (info.getGametype()==null) {
            decideGameType();
        }

        logGameType();

        sendChangeInfo();

        // each game has 8 rounds
        for (int i = 0; i < 8; i++) {
            r.nextRound();
            board.clear();
        }

        if (Settings.verbose>0) {
            for (Player p : getWinners()) {
                System.out.println("Winner is = " + p);
            }
        }
    }

    void createPlayers(int num, int numAI, int trainNum) {
        int human = 0, ai = 0, train = 0;
        for (int i = 0; i < 4; i++) {
            if (human < num) {
                players[i] = new HumanPlayer(this, "HumanPlayer " + i);
                human++;
            } else if (ai < numAI) {
                players[i] = new AgentPlayer(this, "AgentPlayer " + i);
                ai++;
            } else if (train < trainNum){
                players[i] = new TrainAgentPlayer(this, "Train " + i);
                train++;
            } else {
                players[i] = new RandomPlayer(this, "RandomPlayer " + i);
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
        if (Settings.verbose==0) { return; }
        if (info.getGametype() instanceof AceGame g) {
            Player p = g.player;
            int index = Arrays.asList(players).indexOf(p);

            System.out.println("Player " + index + " plays with the " + g.color + " Ace");
        } else {
            System.out.println("Ramsch?");
        }
    }

    public Player[] getWinners() {
        return info.getGametype().getGameWinner(new ArrayList<>(Arrays.asList(players)));
    }

    public ArrayList<AgentPlayer> getAgents() {
        ArrayList<AgentPlayer> agents = new ArrayList<>();
        for (Player p : players) {
            if (p instanceof AgentPlayer ap) {
                agents.add(ap);
            }
        }
        return agents;
    }

    public ArrayList<TrainAgentPlayer> getTrainAgents() {
        ArrayList<TrainAgentPlayer> agents = new ArrayList<>();
        for (Player p : players) {
            if (p instanceof TrainAgentPlayer ap) {
                agents.add(ap);
            }
        }
        return agents;
    }
}
