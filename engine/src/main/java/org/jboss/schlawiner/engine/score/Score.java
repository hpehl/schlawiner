package org.jboss.schlawiner.engine.score;

class Score {

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

    int getDifference() {
        return difference;
    }
}
