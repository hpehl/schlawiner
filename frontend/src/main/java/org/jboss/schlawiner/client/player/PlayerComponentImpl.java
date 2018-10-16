package org.jboss.schlawiner.client.player;

import java.util.List;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import org.gwtproject.event.shared.HandlerRegistration;
import org.gwtproject.event.shared.HandlerRegistrations;
import org.gwtproject.safehtml.shared.SafeHtml;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.gwt.elemento.core.EventType;
import org.jboss.gwt.elemento.core.Key;
import org.jboss.schlawiner.client.resources.Ids;
import org.jboss.schlawiner.engine.game.Player;

import static elemental2.dom.DomGlobal.document;
import static org.gwtproject.safehtml.shared.SafeHtmlUtils.fromSafeConstant;
import static org.gwtproject.safehtml.shared.SafeHtmlUtils.fromString;
import static org.jboss.gwt.elemento.core.Elements.failSafeRemoveFromParent;
import static org.jboss.gwt.elemento.core.Elements.td;
import static org.jboss.gwt.elemento.core.Elements.tr;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.EventType.input;
import static org.jboss.gwt.elemento.core.EventType.keyup;
import static org.jboss.schlawiner.client.resources.CSS.clickable;
import static org.jboss.schlawiner.client.resources.CSS.leftAlign;

public class PlayerComponentImpl extends AbstractComponent<PlayerController, HTMLElement> implements
    PlayerComponent {

    private PlayerView view;
    private HandlerRegistration handler;

    @Override
    public void render() {
        view = PlayerView.create();
        initElement(view.asElement());
    }

    @Override
    public void onAttach() {
        super.onAttach();
        this.handler = HandlerRegistrations.compose(
            EventType.bind(view.addPlayer, keyup, e -> {
                    if (Key.Enter.match(e)) {
                        getController().addPlayer(((HTMLInputElement) e.target).value);
                    }
                }
            ),
            EventType.bind(view.startGame, click, event -> getController().startGame()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) {
            handler.removeHandler();
        }
    }

    @Override
    public void showPlayers(List<Player> players) {
        Elements.removeChildrenFrom(view.tbody);
        for (Player player : players) {
            view.tbody.appendChild(playerRow(player));
        }
    }

    @Override
    public void addPlayer(Player player) {
        view.tbody.appendChild(playerRow(player));
        view.addPlayer.value = "";
    }

    @Override
    public void updateName(Player player) {
        Element element = document.getElementById(nameId(player));
        if (element != null) {
            element.textContent = player.getName();
        }
    }

    @Override
    public void updateHuman(Player player) {
        Element element = document.getElementById(humanId(player));
        if (element != null) {
            SafeHtml html = player.isHuman() ? fromString("✓") : fromSafeConstant("&nbsp;");
            element.innerHTML = html.asString();
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
        return tr().id(player.getId())
            .add(td().css(leftAlign)
                .id(nameId(player))
                .apply(e -> e.setAttribute("contenteditable", "true"))
                .on(input, e -> getController().editPlayer(player.getId(), ((Element) e.target).textContent))
                .textContent(player.getName())
                .title("Click to edit"))
            .add(td().css(clickable)
                .id(humanId(player))
                .on(click, e -> getController().editPlayer(player.getId(), !player.isHuman()))
                .innerHtml(player.isHuman() ? fromString("✓") : fromSafeConstant("&nbsp;")))
            .add(td().css(clickable)
                .on(click, e -> getController().removePlayer(player.getId()))
                .textContent("x"))
            .asElement();
    }

    private String nameId(Player player) {
        return Ids.build(player.getId(), "name");
    }

    private String humanId(Player player) {
        return Ids.build(player.getId(), "human");
    }
}
