package org.jboss.schlawiner.engine.algorithm;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AlgorithmComparisonTest {
    private int diceNumbers[][] = new int[][]{
            {2, 3, 5},
            {4, 4, 4}
    };

    @Test
    void compute() {
        int[] operationResults = computeAlgorithm(new OperationAlgorithm());
        int[] termResults = computeAlgorithm(new TermAlgorithm());
        assertArrayEquals(operationResults, termResults);
    }

    private int[] computeAlgorithm(final Algorithm algorithm) {
        int[] numberOfResults = new int[diceNumbers.length];
        Solutions[] results = new Solutions[diceNumbers.length];

        for (int i = 0; i < diceNumbers.length; i++) {
            Stopwatch timer = Stopwatch.createStarted();
            for (int target = 1; target < 101; target++) {
                results[i] = algorithm.compute(diceNumbers[i][0], diceNumbers[i][1], diceNumbers[i][2], target);
            }
            System.out.format("%s finished with %d results in %s for targets 1..100 using [%d,%d,%d]\n",
                    algorithm.getName(), results[i].size(), timer, diceNumbers[i][0], diceNumbers[i][1], diceNumbers[i][2]);
            numberOfResults[i] = results[i].size();
        }
        return numberOfResults;
    }
}
