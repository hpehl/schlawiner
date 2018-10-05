package org.jboss.schlawiner.engine.score;

public class Score {

    final static Score EMPTY = new Score(null, -1);

    private final String term;
    private final int difference;

    Score(String term, int difference) {
        this.term = term;
        this.difference = difference;
    }

    public String getTerm() {
        return term;
    }

    public int getDifference() {
        return difference;
    }

    @Override
    public String toString() {
        return String.valueOf(term) + " Δ " + difference;
    }
}
