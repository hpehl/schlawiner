package org.jboss.schlawiner.client.settings;

import com.github.nalukit.nalu.client.component.IsComponent;
import org.jboss.schlawiner.engine.game.Level;

public interface SettingsController extends IsComponent.Controller {

    void setName(String name);

    void setNumbers(int numbers);

    void setTimeout(int timeout);

    void setPenalty(int penalty);

    void setRetries(int retries);

    void setAutoDice(boolean autoDice);

    void setLevel(Level level);
}
