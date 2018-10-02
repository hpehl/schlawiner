package org.jboss.schlawiner.engine.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SolutionsTest {
    private Solutions solutions;

    @Test
    void bestSolutionInRange() {
        solutions = new Solutions(23, 10);
        solutions.add(new Solution("term 1", 20));
        solutions.add(new Solution("term 2", 20));
        solutions.add(new Solution("term 3", 22));
        solutions.add(new Solution("term 4", 24));
        solutions.add(new Solution("term 5", 24));
        solutions.add(new Solution("term 6", 24));
        solutions.add(new Solution("term 7", 25));
        solutions.add(new Solution("term 8", 26));

        Solution solution = solutions.bestSolution();
        assertEquals(22, solution.getValue());
    }

    @Test
    void bestSolutionsOutsideRange() {
        solutions = new Solutions(33, 10);
        solutions.add(new Solution("", 50));
        assertNull(solutions.bestSolution());
    }
}
