package org.jboss.schlawiner.client.room;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.core.JsArray;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTableRowElement;
import elemental2.dom.MouseEvent;
import org.gwtproject.event.shared.HandlerRegistration;
import org.gwtproject.event.shared.HandlerRegistrations;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.gwt.elemento.core.EventCallbackFn;
import org.jboss.gwt.elemento.core.EventType;
import org.jboss.gwt.elemento.template.DataElement;
import org.jboss.gwt.elemento.template.Templated;
import org.jboss.schlawiner.client.Modal;
import org.jboss.schlawiner.client.chat.ChatElement;
import org.jboss.schlawiner.client.resources.CSS;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Room;

import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.EventType.keyup;
import static org.jboss.schlawiner.client.resources.CSS.*;
import static org.jboss.schlawiner.client.resources.FontAwesomeSize.xs;

@Templated("rooms.html")
public abstract class RoomsComponentImpl extends AbstractComponent<RoomsController, HTMLElement> implements
    RoomsComponent {

    static RoomsComponent create() {
        return new Templated_RoomsComponentImpl();
    }

    @DataElement HTMLElement message;
    @DataElement HTMLInputElement player;
    @DataElement HTMLElement tbody;
    @DataElement HTMLElement addRoom;
    @DataElement HTMLElement modalContent;
    @DataElement ChatElement chat = new ChatElement();
    private HandlerRegistration handler;
    private Modal modal;

    @Override
    public void render() {
        initElement(asElement());
    }

    @Override
    public void onAttach() {
        super.onAttach();
        this.handler = HandlerRegistrations.compose(
            EventType.bind(player, keyup, e -> getController().setPlayer(((HTMLInputElement) e.target).value)),
            EventType.bind(addRoom, click, event -> prepareAddRoom()));
        this.chat.init(getController().getEventBus(), getController().getPlayer());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) {
            handler.removeHandler();
        }
    }

    private void prepareAddRoom() {
        if (modal != null) {
            modal.destroy();
        }
        Modal.ModalOptions options = new Modal.ModalOptions();
        options.closeMethods = new JsArray<>("overlay", "escape");
        options.onClose = () -> getController().addRoom("New Room", 5);
        Modal modal = new Modal(options);
        HTMLElement content = (HTMLElement) modalContent.cloneNode(true);
        setVisible(content, true);
        modal.setContent(content);
        modal.open();
    }

    @Override
    public void showPlayer(Player player) {
        this.player.value = player.getName();
    }

    @Override
    public void showRooms(Iterable<Room> rooms) {
        removeChildrenFrom(tbody);
        for (Room room : rooms) {
            tbody.appendChild(roomRow(room));
        }
    }

    @Override
    public void selectRoom(Room room) {
        for (HTMLElement tr : children(tbody)) {
            selectRoom(tr, room);
        }
    }

    private void selectRoom(HTMLElement tr, Room room) {
        for (HTMLElement td : Elements.children(tr)) {
            Elements.toggle(td, selected, tr.id.equals(room.getId()));
        }
    }

    @Override
    public void addRoom(Room room) {
        tbody.appendChild(roomRow(room));
    }

    private HTMLElement roomRow(Room room) {
        HTMLElement actions;
        EventCallbackFn<MouseEvent> selectCallback = e -> getController().selectRoom(room);

        HTMLTableRowElement tr = tr().id(room.getId())
            .add(td().css(textLeft, about, textTruncate)
                .textContent(room.getName())
                .on(click, selectCallback))
            .add(td().textContent(String.valueOf(room.getPlayers())).on(click, selectCallback))
            .add(td()
                .on(click, selectCallback)
                .add(actions = div().css(CSS.actions).asElement()))
            .asElement();

        if (getController().isOwnRoom(room)) {
            actions.appendChild(i().css(fas("play", xs), action)
                .title("Start game")
                .on(click, e -> getController().startGame(room))
                .asElement());
            actions.appendChild(i().css(far("trash-alt", xs), action)
                .title("Remove room")
                .on(click, e -> getController().removeRoom(room))
                .asElement());
        } else if (room.getPlayers() < room.getLimit()) {
            actions.appendChild(i().css(fas("sign-in-alt", xs), action)
                .title("Join room")
                .on(click, e -> getController().joinRoom(room))
                .asElement());
        } else {
            actions.innerHTML = "&nbsp;";
        }
        return tr;
    }

    @Override
    public void switchChat(Player player, Room selectedRoom) {
        if (player != null && selectedRoom != null) {
            chat.enable();
        } else {
            chat.disable("Please enter a name and select a room in order to chat.");
        }
    }
}
