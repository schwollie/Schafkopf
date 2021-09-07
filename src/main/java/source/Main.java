package source;

import basicneuralnetwork.NeuralNetwork;
import source.ai.AIGameManager;
import source.display.VisualManager;
import source.game.core.GameManager;

public class Main {

    public static void main(String[] args) {
        //normalGame();
        aiGame();
    }

    private static void normalGame() {
        GameManager g = new GameManager(0, 4);
        VisualManager v = new VisualManager();

        v.start(g);
        g.startGame();
    }

    private static void aiGame() {
        AIGameManager aiGameManager = new AIGameManager();
        aiGameManager.trainAgents();
    }
}
