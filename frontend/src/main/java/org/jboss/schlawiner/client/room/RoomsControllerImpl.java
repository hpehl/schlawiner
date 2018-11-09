package org.jboss.schlawiner.client.room;

import java.util.Collection;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.client.Context;
import org.jboss.schlawiner.client.chat.ChatServiceClient;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Room;

@Controller(route = "/rooms",
    selector = "content",
    component = RoomsComponentImpl.class,
    componentInterface = RoomsComponent.class)
public class RoomsControllerImpl extends AbstractComponentController<Context, RoomsComponent, HTMLElement>
    implements RoomsController {

    private ChatServiceClient chatService;
    private Room selectedRoom;
    private Multimap<Player, Room> roomsByPlayer;


    public RoomsControllerImpl() {
        this.chatService = new ChatServiceClient("http://localhost:8080", null, null);
        this.selectedRoom = null;
        this.roomsByPlayer = ArrayListMultimap.create();
    }

    @Override
    public RoomsComponent createComponent() {
        return RoomsComponentImpl.create();
    }

    @Override
    public void start() {
        // TODO Fetch rooms from server side
        roomsByPlayer.put(new Player("John", true), new Room("Room 1", 5));
        roomsByPlayer.put(new Player("Brian", true),
            new Room("A perfectly normal room with an extremely long name", 4));
        roomsByPlayer.put(context.getCurrentPlayer(), new Room("My room", 6));
        roomsByPlayer.put(new Player("Stewart", true), new Room("Another room", 3));

        getComponent().showPlayer(context.getCurrentPlayer());
        getComponent().showRooms(roomsByPlayer.values());
        getComponent().switchChat(context.getCurrentPlayer(), selectedRoom);
    }

    @Override
    public void setPlayer(String name) {
        context.getCurrentPlayer().setName(name);
        getComponent().switchChat(context.getCurrentPlayer(), selectedRoom);
    }

    @Override
    public boolean isOwnRoom(Room room) {
        Collection<Room> rooms = roomsByPlayer.get(context.getCurrentPlayer());
        for (Room r : rooms) {
            if (r.equals(room)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addRoom(String name, int limit) {
        Room room = new Room(name, limit);
        roomsByPlayer.put(context.getCurrentPlayer(), room);
        getComponent().addRoom(room);
    }

    @Override
    public void selectRoom(Room room) {
        this.selectedRoom = room;
        getComponent().selectRoom(room);
        getComponent().switchChat(context.getCurrentPlayer(), selectedRoom);
    }

    @Override
    public void startGame(Room room) {
    }

    @Override
    public void removeRoom(Room room) {
        if (room.equals(selectedRoom)) {
            selectedRoom = null;
        }
        // TODO remove room from multi map
        getComponent().showRooms(roomsByPlayer.values());
    }

    @Override
    public void joinRoom(Room room) {
    }

    @Override
    public ChatServiceClient getChatService() {
        return chatService;
    }

    @Override
    public Player getPlayer() {
        return context.getCurrentPlayer();
    }
}
