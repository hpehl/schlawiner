package org.jboss.schlawiner.client.game;

import java.util.Date;

import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.client.resources.CSS;
import org.jboss.schlawiner.client.resources.Format;

import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.Elements.input;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.InputType.text;
import static org.jboss.schlawiner.client.resources.CSS.*;
import static org.jboss.schlawiner.client.resources.UIConstants.ARIA_EXPANDED;
import static org.jboss.schlawiner.client.resources.UIConstants.FALSE;
import static org.jboss.schlawiner.client.resources.UIConstants.TRUE;

class ChatElement implements IsElement<HTMLElement> {

    private final HTMLElement root;
    private final HTMLElement slider;
    private final HTMLElement sliderHandle;
    private final HTMLElement container;
    private final HTMLElement history;
    private final HTMLElement disabled;
    private final HTMLElement reason;
    private boolean open;
    private boolean available;

    ChatElement() {
        open = false;
        available = true;
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
                .add(input(text).css(chatInput)
                    .apply(i -> i.placeholder = "Enter message"))
                .asElement())
            .add(disabled = div().css(chatDisabled).add(reason = p().asElement()).asElement())
            .asElement();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    void disable(String why) {
        if (available) {
            reason.textContent = why;
            setVisible(container, false);
            setVisible(disabled, true);
            available = false;
        }
    }

    void enable() {
        if (!available) {
            reason.textContent = "";
            setVisible(disabled, false);
            setVisible(container, true);
            available = true;
        }
    }

    void addLine(String user, Date time, String text) {
        HTMLElement line = span().css(CSS.line)
            .add(span().css(user).textContent("[" + user + "]"))
            .add(time().textContent(Format.time(time)))
            .add(span().css(CSS.text).textContent(text))
            .asElement();
        history.appendChild(line);
        line.scrollIntoView();
    }

    private void collapse() {
        if (open) {
            slider.title = "Open chat";
            sliderHandle.classList.remove("fa-caret-right");
            sliderHandle.classList.add("fa-caret-left");
            Elements.setVisible(container, false);
            Elements.setVisible(disabled, false);
            container.setAttribute(ARIA_EXPANDED, FALSE);
            open = false;
        }
    }

    private void expand() {
        if (!open) {
            slider.title = "Close chat";
            sliderHandle.classList.remove("fa-caret-left");
            sliderHandle.classList.add("fa-caret-right");
            Elements.setVisible(container, available);
            Elements.setVisible(disabled, !available);
            container.setAttribute(ARIA_EXPANDED, TRUE);
            open = true;
        }
    }
}
