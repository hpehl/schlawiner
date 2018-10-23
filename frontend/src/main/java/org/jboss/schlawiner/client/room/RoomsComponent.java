package org.jboss.schlawiner.client.room;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Room;

public interface RoomsComponent extends IsComponent<RoomsController, HTMLElement>, IsElement<HTMLElement> {

    void showPlayer(Player player);

    void showRooms(Iterable<Room> rooms);

    void selectRoom(Room room);

    void addRoom(Room room);

    void switchChat(Player player, Room selectedRoom);
}
