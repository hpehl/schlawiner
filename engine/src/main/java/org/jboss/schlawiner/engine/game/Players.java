package org.jboss.schlawiner.engine.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;

public class Players implements Iterable<Player> {

    private final List<Player> players;
    private Iterator<Player> iterator;
    private Player me;
    private Player computer;
    private Player current;

    public Players() {
        this.players = new ArrayList<>();
        reset();
    }

    void defaultPlayers() {
        me = new Player("Player 1", true);
        players.add(me);
        computer = new Player("Computer", false);
        players.add(computer);
    }

    public void reset() {
        iterator = Iterators.cycle(players);
    }

    public Player next() {
        if (iterator.hasNext()) {
            current = iterator.next();
        }
        return current;
    }

    public boolean isFirst() {
        return current != null && players.indexOf(current) == 0;
    }

    public boolean isLast() {
        return current != null && players.indexOf(current) == players.size() - 1;
    }

    public void add(Player player) {
        players.add(player);
    }

    public void remove(Player player) {
        if (player.equals(current)) {
            current = null;
        }
        players.remove(player);
    }

    public Player current() {
        return current;
    }

    public Player me() {
        return me;
    }

    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

    public int indexOf(Player p) {
        return players.indexOf(p);
    }

    public int size() {
        return players.size();
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public void clear() {
        players.clear();
    }
}
