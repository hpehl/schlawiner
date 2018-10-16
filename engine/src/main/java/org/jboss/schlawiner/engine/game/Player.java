package org.jboss.schlawiner.engine.game;

import java.util.Objects;

public class Player {

    private final String id;
    private String name;
    private boolean human;
    private int retries;

    public Player(String name, boolean human) {
        this(UUID.get(), name, human);
    }

    public Player(String id, String name, boolean human) {
        this.id = id;
        this.name = name;
        this.human = human;
        this.retries = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Player)) { return false; }
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + (human ? ": human" : ": computer");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHuman() {
        return human;
    }

    public void setHuman(boolean human) {
        this.human = human;
    }

    public int getRetries() {
        return retries;
    }

    void setRetries(int retries) {
        this.retries = retries;
    }

    void retry() {
        retries--;
    }
}
