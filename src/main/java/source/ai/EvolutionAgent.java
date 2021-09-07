package source.ai;

import basicneuralnetwork.NeuralNetwork;
import source.game.cards.Card;
import source.game.core.GameManager;
import source.game.player.AgentPlayer;
import source.game.player.Player;

import java.util.Comparator;

public class EvolutionAgent implements Comparator<EvolutionAgent> {

    NeuralNetwork network;
    AgentPlayer player;

    public float fitness;

    public EvolutionAgent() {
        this.network = AIGameManager.getDefaultNet();
    }

    public EvolutionAgent(NeuralNetwork network) {
        this.network = network;
    }

    public void setupAgent(AgentPlayer p) {
        p.setNeuralNetwork(this.network);
        this.player = p;
    }

    public void setFitness(GameManager manager) {
        for (Player p : manager.getWinners()) {
            if (p == this.player) {
                fitness += 1;
                //fitness += this.player.getPoints()/120.0;
            }
        }
    }

    @Override
    public int compare(EvolutionAgent o1, EvolutionAgent o2) {
        float p1 = o1.fitness;
        float p2 = o2.fitness;

        if (p1 > p2) {
            return 1;
        } else if (p1 < p2) {
            return -1;
        } else {
            return 0;
        }
    }
}
