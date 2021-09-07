package source.ai;

import basicneuralnetwork.NeuralNetwork;
import source.Settings;

public class AIGameManager {

    EvolutionManager evm;

    public AIGameManager() {
        evm = new EvolutionManager(25);
    }

    public static NeuralNetwork getDefaultNet() {
        return new NeuralNetwork(37, 2, 10, 32);
    }

    public void trainAgents() {
        for (int gen = 0; gen < 100000; gen++) {
            if (Settings.verbose_AI>0) {
                System.out.println("train generation " + gen);
            }
            evm.calcFitness();
            evm.testRealFitness();
            if (Settings.verbose_AI>0) {
                System.out.println("evolve");
            }
            evm.evolveGen();
        }
    }
}
