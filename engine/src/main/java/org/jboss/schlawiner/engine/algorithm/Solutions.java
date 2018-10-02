package org.jboss.schlawiner.engine.algorithm;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.jboss.schlawiner.engine.game.Level;

import static java.lang.Math.abs;

public class Solutions {

    private final int target;
    private final int allowedDifference;
    private final SortedSetMultimap<Integer, Solution> solutions;

    public Solutions(int target, int allowedDifference) {
        this.target = target;
        this.allowedDifference = allowedDifference;
        this.solutions = TreeMultimap.create();
    }

    public void add(Solution solution) {
        if (solution.getValue() >= target - allowedDifference && solution.getValue() <= target + allowedDifference) {
            solutions.put(abs(solution.getValue() - target), solution);
        }
    }

    public Solution bestSolution() {
        if (solutions.isEmpty()) {
            return null;
        }
        return solutions.values().iterator().next();
    }

    public Solution bestSolution(Level level) {
        // TODO Choose bestSolution based on level
        return bestSolution();
    }

    public int size() {
        return solutions.size();
    }
}
