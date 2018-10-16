package org.jboss.schlawiner.engine.game;

import static org.jboss.schlawiner.engine.game.Level.MEDIUM;

public class Settings {

    private int timeout; // in seconds
    private int penalty;
    private int retries;
    private int numbers; // actually the number of randomly generated numbers
    private boolean autoDice;
    private Level level;

    public Settings() {
        // defaults
        this.timeout = 60;
        this.penalty = 5;
        this.retries = 3;
        this.numbers = 8;
        this.autoDice = false;
        this.level = MEDIUM;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public boolean isAutoDice() {
        return autoDice;
    }

    public void setAutoDice(boolean autoDice) {
        this.autoDice = autoDice;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
