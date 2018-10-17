package org.jboss.schlawiner.engine.game;

import org.jboss.schlawiner.engine.algorithm.Solution;

public class Calculation {

    private final int difference;
    private final int bestDifference;
    private final Solution bestSolution;

    Calculation(int difference, int currentNumber, Solution bestSolution) {
        this.difference = difference;
        this.bestSolution = bestSolution;
        this.bestDifference = bestSolution == null ? difference : Math.abs(bestSolution.getValue() - currentNumber);
    }

    public boolean isBest() {
        return difference == 0 || difference == bestDifference;
    }

    public int getDifference() {
        return difference;
    }

    public int getBestDifference() {
        return bestDifference;
    }

    public Solution getBestSolution() {
        return bestSolution;
    }
}
