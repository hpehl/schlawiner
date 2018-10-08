package org.jboss.schlawiner.client.main;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;

import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.gwt.elemento.core.Elements.h;
import static org.jboss.gwt.elemento.core.Elements.p;

public class MainComponentImpl extends AbstractComponent<MainController, HTMLElement> implements MainComponent {

    @Override
    public void render() {
        initElement(div()
            .add(h(1, "Main Menu"))
            .add(p().textContent("Not yet implemented!"))
            .asElement());
    }
}
