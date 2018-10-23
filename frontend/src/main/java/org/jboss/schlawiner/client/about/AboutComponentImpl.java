package org.jboss.schlawiner.client.about;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.template.Templated;

@Templated("about.html")
public abstract class AboutComponentImpl extends AbstractComponent<AboutController, HTMLElement>
    implements AboutComponent {

    static AboutComponent create() {
        return new Templated_AboutComponentImpl();
    }

    @Override
    public void render() {
        initElement(asElement());
    }
}
