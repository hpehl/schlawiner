package org.jboss.schlawiner.client.about;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.client.Context;

@Controller(route = "/about",
    selector = "content",
    component = AboutComponentImpl.class,
    componentInterface = AboutComponent.class)
public class AboutControllerImpl extends AbstractComponentController<Context, AboutComponent, HTMLElement>
    implements AboutController {

    @Override
    public AboutComponent createComponent() {
        return AboutComponentImpl.create();
    }
}
