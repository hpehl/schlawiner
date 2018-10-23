package org.jboss.schlawiner.client.chat;

import java.util.Date;

import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import org.gwtproject.event.shared.EventBus;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.gwt.elemento.core.Key;
import org.jboss.schlawiner.client.resources.CSS;
import org.jboss.schlawiner.client.resources.Format;
import org.jboss.schlawiner.engine.game.Player;

import static com.google.common.base.Strings.emptyToNull;
import static elemental2.dom.DomGlobal.document;
import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.Elements.input;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.EventType.keyup;
import static org.jboss.gwt.elemento.core.InputType.text;
import static org.jboss.schlawiner.client.resources.CSS.*;
import static org.jboss.schlawiner.client.resources.UIConstants.ARIA_EXPANDED;
import static org.jboss.schlawiner.client.resources.UIConstants.FALSE;
import static org.jboss.schlawiner.client.resources.UIConstants.TRUE;

public class ChatElement implements IsElement<HTMLElement> {

    private final HTMLElement root;
    private final HTMLElement slider;
    private final HTMLElement sliderHandle;
    private final HTMLElement container;
    private final HTMLElement history;
    private final HTMLInputElement input;
    private final HTMLElement disabled;
    private final HTMLElement reason;
    private boolean open;
    private boolean available;
    private EventBus eventBus;
    private Player player;

    public ChatElement() {
        root = div().css(chat)
            .add(slider = div().css(chatSlider, clickable)
                .title("Open chat")
                .on(click, e -> {
                    if (open) {
                        collapse();
                    } else {
                        expand();
                    }
                })
                .add(sliderHandle = i().css(fas("care-left"), alignSelfCenter).asElement())
                .asElement())
            .add(container = div().css(chatContainer).aria(ARIA_EXPANDED, TRUE)
                .add(history = div().css(chatHistory, overflowY).asElement())
                .add(input = input(text).css(chatInput)
                    .apply(i -> i.placeholder = "Enter message")
                    .on(keyup, e -> {
                        if (eventBus != null && Key.Enter.match(e)) {
                            HTMLInputElement input = (HTMLInputElement) e.target;
                            if (emptyToNull(input.value) != null) {
                                eventBus.fireEvent(new ChatMessageEvent(new ChatMessage(player, input.value)));
                                input.value = "";
                            }
                        }
                    })
                    .asElement())
                .add(disabled = div().css(chatDisabled).add(reason = p().asElement()).asElement())
                .asElement())
            .asElement();
        collapse();
    }

    public void init(EventBus eventBus, Player player) {
        this.eventBus = eventBus;
        this.player = player;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    public void disable(String why) {
        reason.textContent = why;
        setVisible(history, false);
        setVisible(input, false);
        setVisible(disabled, true);
        available = false;
    }

    public void enable() {
        reason.textContent = "";
        setVisible(disabled, false);
        setVisible(history, true);
        setVisible(input, true);
        available = true;
    }

    public void addLine(String user, Date time, String text) {
        HTMLElement line = span().css(CSS.line)
            .add(span().css(user).textContent("[" + user + "]"))
            .add(time().textContent(Format.time(time)))
            .add(span().css(CSS.text).textContent(text))
            .asElement();
        history.appendChild(line);
        line.scrollIntoView();
    }

    private void collapse() {
        slider.title = "Open chat";
        sliderHandle.classList.remove("fa-caret-right");
        sliderHandle.classList.add("fa-caret-left");
        setVisible(container, false);
        container.setAttribute(ARIA_EXPANDED, FALSE);
        Element page = document.querySelector("." + CSS.page);
        if (page != null) {
            page.classList.remove(pageWithOpenChat);
        }
        open = false;
    }

    private void expand() {
        slider.title = "Close chat";
        sliderHandle.classList.remove("fa-caret-left");
        sliderHandle.classList.add("fa-caret-right");
        setVisible(container, true);
        container.setAttribute(ARIA_EXPANDED, TRUE);
        Element page = document.querySelector("." + CSS.page);
        if (page != null) {
            page.classList.add(pageWithOpenChat);
        }
        open = true;
    }
}
