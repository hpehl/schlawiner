package org.jboss.schlawiner.client.about;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;

public class AboutComponentImpl extends AbstractComponent<AboutController, HTMLElement> implements AboutComponent {

    @Override
    public void render() {
        initElement(AboutView.create().asElement());
    }
}
