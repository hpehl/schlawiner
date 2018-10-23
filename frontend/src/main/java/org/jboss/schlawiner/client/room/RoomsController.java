package org.jboss.schlawiner.client.room;

import com.github.nalukit.nalu.client.component.IsComponent;
import com.github.nalukit.nalu.client.component.IsComponentCreator;
import org.gwtproject.event.shared.EventBus;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Room;

public interface RoomsController extends IsComponent.Controller, IsComponentCreator<RoomsComponent> {

    void setPlayer(String value);

    boolean isOwnRoom(Room room);

    void addRoom(String name, int limit);

    void selectRoom(Room room);

    void startGame(Room room);

    void removeRoom(Room room);

    void joinRoom(Room room);

    EventBus getEventBus();

    Player getPlayer();
}
