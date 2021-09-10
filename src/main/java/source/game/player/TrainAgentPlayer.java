package source.game.player;

import basicneuralnetwork.NeuralNetwork;
import source.Settings;
import source.ai.AIGameManager;
import source.ai.InputGenerator;
import source.game.cards.Card;
import source.game.core.GameManager;
import source.game.core.gametype.Gametype;
import source.game.stack.Stack;

import java.util.ArrayList;
import java.util.Random;

public class TrainAgentPlayer extends Player{

    NeuralNetwork neuralNetwork;

    public TrainAgentPlayer(GameManager game, NeuralNetwork network) {
        super(game, "AI_Agent");
        this.neuralNetwork = network;
    }

    public TrainAgentPlayer(GameManager game, String name) {
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

        ArrayList<Card> pcards = g.getInfo().getGametype().possibleCards(this, g);

        for (int i = 0; i<out.length; i++) {
            out[i] = 0;
        }

        for (Card c : pcards) {
            out[orderdCards.getCards().indexOf(c)] = 1;
        }

        neuralNetwork.train(input, out);
        if (Settings.verbose_AI>5) {
            System.out.println(neuralNetwork.getError(input, out));
        }

        Random rnd = new Random();
        this.layCard(pcards.get(rnd.nextInt(pcards.size())));
    }

    private void train(double[] input) {

    }

    @Override
    public Gametype selectGameType() {
        return super.selectGameType();
    }



    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }
}
