package org.jboss.schlawiner.client.about;

import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.gwt.elemento.template.Templated;

@Templated
abstract class AboutView implements IsElement<HTMLElement> {

    static AboutView create() {
        return new Templated_AboutView();
    }
}
