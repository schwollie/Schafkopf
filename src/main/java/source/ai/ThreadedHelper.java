package source.ai;

import source.Settings;
import source.game.core.GameManager;
import source.game.player.AgentPlayer;

import java.util.ArrayList;
import java.util.Collections;

public class ThreadedHelper extends Thread{

    private ArrayList<EvolutionAgent> agents;
    private int numGames;

    public ThreadedHelper(ArrayList<EvolutionAgent> agentPlayers, int numGames) {
        this.agents = new ArrayList<>(agentPlayers);
        this.numGames = numGames;
    }

    @Override
    public void run() {
        // reset fitness
        for (EvolutionAgent ea: agents) {
            ea.fitness = 0;
        }

        // play games
        for (int g = 0; g < numGames; g++) {
            //Collections.shuffle(agents);

            //if (Settings.verbose_AI>1) {
             //   System.out.println("Game " + g);
            //}

            for (int i = 0; i < agents.size(); i+=4) {

                GameManager gameManager = new GameManager(0, 4, 0);
                for (int a = 0; a < 4; a++) {
                    agents.get(i+a).setupAgent(gameManager.getAgents().get(a));
                }

                gameManager.startGame();

                // determine fitness
                for (int a = 0; a < 4; a++) {
                    agents.get(i+a).setFitness(gameManager);
                }
            }
        }
    }
}
