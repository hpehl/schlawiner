package org.jboss.schlawiner.engine.game;

import java.util.Objects;

public class Player {

    private final String name;
    private final boolean human;
    private int retries;

    public Player(String name, boolean human) {
        this.name = name;
        this.human = human;
        this.retries = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Player)) { return false; }
        Player player = (Player) o;
        return human == player.human &&
            Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, human);
    }

    @Override
    public String toString() {
        return name + (human ? ": human" : ": computer");
    }

    public void retry() {
        retries--;
    }

    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return human;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }
}
