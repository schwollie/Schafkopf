package source.ai;

import basicneuralnetwork.NeuralNetwork;
import source.Logic;
import source.Settings;
import source.game.core.GameManager;
import source.game.core.gametype.NoType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class EvolutionManager {

    public static final int games_per_agent = 1000;
    public static final int test_game_count = 10000;

    public static final double crossover = 0.15;
    public static final double mutatePropability = 0.2;
    public static final double mutateStrength = 0.3;

    ArrayList<EvolutionAgent> agents = new ArrayList<>();

    public EvolutionManager(int numAgents) {
        numAgents = (numAgents/(Settings.cores*4)*(Settings.cores*4)); // 4 agents play with each other
        for (int i = 0; i<numAgents; i++) {
            agents.add(new EvolutionAgent());
        }
    }

    public NeuralNetwork preTrain(int x) {
        NeuralNetwork nn = AIGameManager.getDefaultNet();
        for (int i = 0; i < x; i++) {
            GameManager manager = new GameManager(0,0,1);
            manager.getTrainAgents().get(0).setNeuralNetwork(nn);
            manager.startGame();
        }
        return nn;
    }

    public void calcFitnessThreaded() {
        ArrayList<ThreadedHelper> helpers = new ArrayList<>();
        for (int i = 0; i < Settings.cores; i++) {
            ArrayList<EvolutionAgent> sublist = (ArrayList<EvolutionAgent>)
                    Logic.chopIntoParts(agents, Settings.cores).get(i);


            ThreadedHelper helper = new ThreadedHelper(sublist, games_per_agent);
            helper.start();
            helpers.add(helper);
            try {
                helper.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (ThreadedHelper h : helpers) {
            try {
                h.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // gives each agent a fitness score
    public void calcFitness() {
        // reset fitness
        for (EvolutionAgent ea: agents) {
            ea.fitness = 0;
        }

        // play games
        for (int g = 0; g < games_per_agent; g++) {
            Collections.shuffle(agents);

            if (Settings.verbose_AI>1) {
                System.out.println("Game " + g);
            }

            for (int i = 0; i < agents.size(); i++) {

                GameManager gameManager = new GameManager(0, 1, 0);
                for (int a = 0; a < 1; a++) {
                    agents.get(i+a).setupAgent(gameManager.getAgents().get(a));
                }

                gameManager.startGame();

                // determine fitness
                for (int a = 0; a < 1; a++) {
                    agents.get(i+a).setFitness(gameManager);
                }
            }
        }
    }

    // test lowest score and highest against random players
    public void testRealFitness() {
        if (Settings.verbose_AI>2) {
            System.out.println("test real fitness");
        }
        // sort agents
        agents.sort(new EvolutionAgent());

        EvolutionAgent lowest = agents.get(0);
        EvolutionAgent highest = agents.get(agents.size()-1);

        float oldFitnessLow = lowest.fitness;
        float oldFitnessHigh = highest.fitness;
        lowest.fitness = 0;
        highest.fitness = 0;

        for (int i = 0; i < test_game_count; i++) {
            GameManager gameManager = new GameManager(0, 1, 0);
            lowest.setupAgent(gameManager.getAgents().get(0));
            gameManager.startGame();
            lowest.setFitness(gameManager);

            GameManager gameManager2 = new GameManager(0, 1, 0);
            highest.setupAgent(gameManager2.getAgents().get(0));
            gameManager2.startGame();
            highest.setFitness(gameManager2);
        }

        if (Settings.verbose_AI>0) {
            System.out.println("lowest has a score of : " + lowest.fitness + " in " + test_game_count + " Games");
            System.out.println("highest has a score of : " + highest.fitness + " in " + test_game_count + " Games");
        }

        lowest.fitness = oldFitnessLow;
        highest.fitness = oldFitnessHigh;
    }

    public void evolveGen() {
        agents.sort(new EvolutionAgent());

        ArrayList<EvolutionAgent> newAgents = new ArrayList<>();

        int index = 0;
        EvolutionAgent pre = agents.get(0);

        Random rnd = new Random();

        for (EvolutionAgent agent : agents) {

            if ((float)index/agents.size()>1-crossover) {
                newAgents.add(new EvolutionAgent(pre.network.copy().merge(agent.network.copy())));
            }

            if (rnd.nextInt(1000)<= mutatePropability*1000) {
                agent.network.mutate(mutateStrength);
            }

            pre = agent;
            index++;
        }

        for (int i = 0; i< newAgents.size(); i++) {
            agents.get(i).network = newAgents.get(i).network.copy();
        }

    }

    public void setStartNetwork(NeuralNetwork start) {
        for (EvolutionAgent agent : agents) {
            agent.network = start.copy();
        }
    }
}
