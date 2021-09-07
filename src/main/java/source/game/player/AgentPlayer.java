package source.game.player;

import basicneuralnetwork.NeuralNetwork;
import source.ai.AIGameManager;
import source.ai.InputGenerator;
import source.game.cards.Card;
import source.game.core.GameManager;
import source.game.core.gametype.Gametype;
import source.game.stack.Stack;

import java.util.ArrayList;

public class AgentPlayer extends Player{

    NeuralNetwork neuralNetwork;

    public AgentPlayer(GameManager game, NeuralNetwork network) {
        super(game, "AI_Agent");
        this.neuralNetwork = network;
    }

    public AgentPlayer(GameManager game, String name) {
        super(game, name);
        this.neuralNetwork = AIGameManager.getDefaultNet();
    }

    @Override
    public void playRound(GameManager g) {
        super.playRound(g);
        double[] input = InputGenerator.generateInput(g.getInfo(), this);
        double[] out = neuralNetwork.guess(input);

        Stack orderdCards = new Stack();
        orderdCards.createDefaultStack();

        double highestVal = Double.MIN_VALUE;
        Card highest = null;

        for (Card c : cards) {
            double val = out[orderdCards.getCards().indexOf(c)];
            if (val > highestVal) {
                highest = c;
                highestVal = val;
            }
        }
        layCard(highest);
    }

    @Override
    public Gametype selectGameType() {
        return super.selectGameType();
    }



    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }
}
