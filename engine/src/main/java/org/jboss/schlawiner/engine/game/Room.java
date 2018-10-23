package org.jboss.schlawiner.engine.game;

import java.util.Objects;

public class Room {

    private final String id;
    private final String name;
    private final int limit;
    private int players;

    public Room(String name, int limit) {
        this(UUID.get(), name, limit);
    }

    public Room(String id, String name, int limit) {
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.players = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Room)) { return false; }
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Room(" + name + ": " + players + " / " + limit + ")";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLimit() {
        return limit;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }
}
