package org.jboss.schlawiner.client.settings;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.client.Context;
import org.jboss.schlawiner.engine.game.Level;

@Controller(route = "/settings",
    selector = "content",
    component = SettingsComponentImpl.class,
    componentInterface = SettingsComponent.class)
public class SettingsControllerImpl extends AbstractComponentController<Context, SettingsComponent, HTMLElement>
    implements SettingsController {

    @Override
    public void start() {
        super.start();
        component.setController(this);
        component.showSettings(context.getSettings());
    }

    @Override
    public void setName(String name) {
        context.getSettings().setName(name);
        LocalStorage.save(context.getSettings());
    }

    @Override
    public void setNumbers(int numbers) {
        context.getSettings().setNumbers(numbers);
        LocalStorage.save(context.getSettings());
    }

    @Override
    public void setTimeout(int timeout) {
        context.getSettings().setTimeout(timeout);
        LocalStorage.save(context.getSettings());
    }

    @Override
    public void setPenalty(int penalty) {
        context.getSettings().setPenalty(penalty);
        LocalStorage.save(context.getSettings());
    }

    @Override
    public void setRetries(int retries) {
        context.getSettings().setRetries(retries);
        LocalStorage.save(context.getSettings());
    }

    @Override
    public void setAutoDice(boolean autoDice) {
        context.getSettings().setAutoDice(autoDice);
        LocalStorage.save(context.getSettings());
    }

    @Override
    public void setLevel(Level level) {
        context.getSettings().setLevel(level);
        LocalStorage.save(context.getSettings());
    }
}
