package org.jboss.schlawiner.client;

import com.github.nalukit.nalu.client.application.IsContext;
import org.jboss.schlawiner.engine.game.Settings;

public class Context implements IsContext {

    private Settings settings;

    Context() {
        settings = new Settings();
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
