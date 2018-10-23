package org.jboss.schlawiner.client.main;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.client.Context;

@Controller(route = "/main",
    selector = "content",
    component = MainComponentImpl.class,
    componentInterface = MainComponent.class)
public class MainControllerImpl extends AbstractComponentController<Context, MainComponent, HTMLElement>
    implements MainController {

    @Override
    public MainComponent createComponent() {
        return MainComponentImpl.create();
    }
}
