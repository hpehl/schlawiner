package org.jboss.schlawiner.client.game;

import elemental2.dom.HTMLElement;
import org.gwtproject.safehtml.shared.SafeHtml;
import org.jboss.gwt.elemento.core.IsElement;

import static org.gwtproject.safehtml.shared.SafeHtmlUtils.fromSafeConstant;
import static org.jboss.gwt.elemento.core.Elements.p;

class MessageElement implements IsElement<HTMLElement> {

    private final HTMLElement root;

    MessageElement() {
        root = p().innerHtml(fromSafeConstant("&nbsp;")).asElement();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    void setMessage(String message) {
        root.textContent = message;
    }

    void setMessage(SafeHtml message) {
        root.innerHTML = message.asString();
    }
}
