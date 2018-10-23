package org.jboss.schlawiner.client.main;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.template.Templated;
import org.jboss.schlawiner.client.about.AboutComponentImpl;
import org.jboss.schlawiner.client.about.Templated_AboutComponentImpl;

@Templated("main.html")
public abstract class MainComponentImpl extends AbstractComponent<MainController, HTMLElement>
    implements MainComponent {

    static MainComponentImpl create() {
        return new Templated_MainComponentImpl();
    }

    @Override
    public void render() {
        initElement(asElement());
    }
}
