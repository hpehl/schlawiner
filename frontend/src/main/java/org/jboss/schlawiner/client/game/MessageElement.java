package org.jboss.schlawiner.client.game;

import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.IsElement;

import static elemental2.dom.DomGlobal.clearTimeout;
import static elemental2.dom.DomGlobal.setTimeout;
import static org.gwtproject.safehtml.shared.SafeHtmlUtils.fromSafeConstant;
import static org.jboss.gwt.elemento.core.Elements.p;
import static org.jboss.schlawiner.client.resources.UIConstants.MESSAGE_TIMEOUT;

class MessageElement implements IsElement<HTMLElement> {

    private final HTMLElement root;
    private double handle;

    MessageElement() {
        root = p().innerHtml(fromSafeConstant("&nbsp;")).asElement();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    void setMessage(String message) {
        root.textContent = message;
        clearTimeout(handle);
        handle = setTimeout(o -> root.innerHTML = "&nbsp;", MESSAGE_TIMEOUT);
    }
}
