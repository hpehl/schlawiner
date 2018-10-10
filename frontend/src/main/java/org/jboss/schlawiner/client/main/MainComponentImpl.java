package org.jboss.schlawiner.client.main;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;

public class MainComponentImpl extends AbstractComponent<MainController, HTMLElement> implements MainComponent {

    @Override
    public void render() {
        initElement(MainView.create().asElement());
    }
}
