package org.jboss.schlawiner.client.main;

import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.gwt.elemento.template.Templated;

@Templated
abstract class MainView implements IsElement<HTMLElement> {

    static MainView create() {
        return new Templated_MainView();
    }
}
