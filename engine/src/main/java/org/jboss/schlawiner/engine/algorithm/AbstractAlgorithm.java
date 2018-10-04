package org.jboss.schlawiner.engine.algorithm;

public abstract class AbstractAlgorithm implements Algorithm {

    // Calculated by org.jboss.schlawiner.engine.algorithm.FindDifference
    private final static int DEFAULT_DIFFERENCE = 15;

    private final static int[][] MULTIPLIERS = new int[][]{
        {1, 1, 1},
        {1, 1, 10},
        {1, 10, 1},
        {10, 1, 1},
        {1, 1, 100},
        {1, 100, 1},
        {100, 1, 1},
        {1, 10, 10},
        {10, 1, 10},
        {10, 10, 1},
        {10, 10, 10},
        {10, 10, 100},
        {10, 100, 10},
        {100, 10, 10},
        {1, 100, 100},
        {100, 1, 100},
        {100, 100, 1},
        {10, 100, 100},
        {100, 10, 100},
        {100, 100, 10},
        {100, 100, 100},
        {1, 10, 100},
        {1, 100, 10},
        {10, 1, 100},
        {10, 100, 1},
        {100, 1, 10},
        {100, 10, 1},
    };

    private final int allowedDifference;

    AbstractAlgorithm() {
        this(DEFAULT_DIFFERENCE);
    }

    AbstractAlgorithm(int allowedDifference) {
        this.allowedDifference = allowedDifference;
    }

    @Override
    public Solutions compute(int a, int b, int c, int target) {
        Solutions solutions = new Solutions(target, allowedDifference);
        for (int[] multiplier : MULTIPLIERS) {
            int am = a * multiplier[0];
            int bm = b * multiplier[1];
            int cm = c * multiplier[2];
            computePermutation(am, bm, cm, target, solutions);
        }
        return solutions;
    }

    protected abstract void computePermutation(int a, int b, int c, int target, Solutions solutions);

    boolean sameDiceNumbers(int a, int b, int c) {
        return a == b && a == c;
    }
}
