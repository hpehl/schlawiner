package org.jboss.schlawiner.client.settings;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.engine.game.Settings;

public interface SettingsComponent extends IsComponent<SettingsController, HTMLElement> {

    void showSettings(Settings settings);
}
