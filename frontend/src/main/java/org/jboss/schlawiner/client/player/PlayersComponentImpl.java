package org.jboss.schlawiner.client.player;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTableRowElement;
import org.gwtproject.event.shared.HandlerRegistration;
import org.gwtproject.event.shared.HandlerRegistrations;
import org.jboss.gwt.elemento.core.EventType;
import org.jboss.gwt.elemento.core.Key;
import org.jboss.gwt.elemento.template.DataElement;
import org.jboss.gwt.elemento.template.Templated;
import org.jboss.schlawiner.client.resources.Ids;
import org.jboss.schlawiner.engine.game.Player;

import static elemental2.dom.DomGlobal.document;
import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.EventType.keyup;
import static org.jboss.schlawiner.client.resources.CSS.*;

@Templated("players.html")
public abstract class PlayersComponentImpl extends AbstractComponent<PlayersController, HTMLElement> implements
    PlayersComponent {

    static PlayersComponent create() {
        return new Templated_PlayersComponentImpl();
    }

    @DataElement HTMLElement tbody;
    @DataElement HTMLInputElement addPlayer;
    @DataElement HTMLElement startGame;
    private HandlerRegistration handler;

    @Override
    public void render() {
        initElement(asElement());
    }

    @Override
    public void onAttach() {
        super.onAttach();
        this.handler = HandlerRegistrations.compose(
            EventType.bind(addPlayer, keyup, e -> {
                    if (Key.Enter.match(e)) {
                        getController().addPlayer(((HTMLInputElement) e.target).value);
                    }
                }
            ),
            EventType.bind(startGame, click, event -> getController().startGame()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) {
            handler.removeHandler();
        }
    }

    @Override
    public void showPlayers(Iterable<Player> players) {
        removeChildrenFrom(tbody);
        for (Player player : players) {
            tbody.appendChild(playerRow(player));
        }
    }

    @Override
    public void addPlayer(Player player) {
        tbody.appendChild(playerRow(player));
        addPlayer.value = "";
    }

    @Override
    public void updateHuman(Player player) {
        Element element = document.getElementById(humanId(player));
        if (element != null) {
            removeChildrenFrom(element);
            if (player.isHuman()) {
                element.appendChild(i().css(fas("check")).asElement());
            } else {
                element.innerHTML = "&nbsp;";
            }
        }
    }

    @Override
    public void removePlayer(Player player) {
        Element element = document.getElementById(player.getId());
        if (element != null) {
            failSafeRemoveFromParent(element);
        }
    }

    private HTMLElement playerRow(Player player) {
        Element human;
        HTMLTableRowElement row = tr().id(player.getId())
            .add(td().css(textLeft)
                .textContent(player.getName()))
            .add(human = td().css(clickable)
                .id(humanId(player))
                .title("Toggle human and computer player")
                .on(click, e -> getController().togglePlayer(player, !player.isHuman()))
                .asElement())
            .add(td().css(actions)
                .add(i()
                    .css(far("trash-alt"), clickable)
                    .title("Remove player")
                    .on(click, e -> getController().removePlayer(player))))
            .asElement();
        if (player.isHuman()) {
            human.appendChild(i().css(fas("check")).asElement());
        } else {
            human.innerHTML = "&nbsp;";
        }
        return row;
    }

    private String humanId(Player player) {
        return Ids.build(player.getId(), "human");
    }
}
