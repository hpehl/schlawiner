package org.jboss.schlawiner.engine.game;

import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Iterators.cycle;
import static java.util.Collections.unmodifiableList;

public class Players implements Iterable<Player> {

    private final List<Player> players;
    private final Iterator<Player> iterator;
    private Player current;

    public Players(List<Player> players) {
        this.players = unmodifiableList(players);
        this.iterator = cycle(players);
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

    public Player current() {
        return current;
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
}
