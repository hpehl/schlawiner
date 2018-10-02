package org.jboss.schlawiner.engine.algorithm;

import com.google.common.collect.ComparisonChain;

public class Solution implements Comparable<Solution> {

    public static final Solution INVALID = new Solution("Invalid term", Integer.MAX_VALUE);
    private final int value;
    private final String term;


    public Solution(String term, int value) {
        this.value = value;
        this.term = term;
    }

    @Override
    public int compareTo(Solution other) {
        return ComparisonChain.start().compare(value, other.value).compare(term, other.term).result();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Solution)) {
            return false;
        }

        Solution solution = (Solution) o;
        return value == solution.value && !(term != null ? !term.equals(solution.term) : solution.term != null);
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (term != null ? term.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return term + " = " + value;
    }

    public int getValue() {
        return value;
    }

    public String getTerm() {
        return term;
    }
}
