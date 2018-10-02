package org.jboss.schlawiner.engine.algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationAlgorithmTest {
    private OperationAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        algorithm = new OperationAlgorithm();
    }

    @Test
    void compute() {
        Solutions solutions = algorithm.compute(2, 3, 5, 15);
        Solution bestSolution = solutions.bestSolution();
        assertEquals("30 + 5 - 20 = 15", bestSolution.toString());
    }
}
