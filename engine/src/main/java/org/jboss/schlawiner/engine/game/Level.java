package org.jboss.schlawiner.engine.game;

public enum Level {
    EASY(4), MEDIUM(2), HARD(0);

    private final int maxDifference;

    Level(int maxDifference) {
        this.maxDifference = maxDifference;
    }

    public int maxDifference() {
        return maxDifference;
    }
}
