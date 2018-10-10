package org.jboss.schlawiner.client.settings;

import elemental2.webstorage.Storage;
import elemental2.webstorage.WebStorageWindow;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import org.jboss.schlawiner.engine.game.Level;
import org.jboss.schlawiner.engine.game.Settings;

import static elemental2.core.Global.JSON;
import static elemental2.dom.DomGlobal.window;
import static jsinterop.annotations.JsPackage.GLOBAL;

@SuppressWarnings("WeakerAccess")
@JsType(isNative = true, namespace = GLOBAL, name = "Object")
public class LocalStorage {

    String name;
    int numbers;
    int timeout;
    int penalty;
    int retries;
    boolean autoDice;
    String level;

    @JsOverlay
    public final static Settings load() {
        Settings settings = new Settings();
        Storage storage = WebStorageWindow.of(window).localStorage;
        if (storage != null) {
            String item = storage.getItem("schlawiner.settings");
            if (item != null) {
                LocalStorage ls = Js.cast(JSON.parse(item));
                settings.setName(ls.name);
                settings.setNumbers(ls.numbers);
                settings.setTimeout(ls.timeout);
                settings.setPenalty(ls.penalty);
                settings.setRetries(ls.retries);
                settings.setAutoDice(ls.autoDice);
                settings.setLevel(Level.valueOf(ls.level.toUpperCase()));
            }
        }
        return settings;
    }

    @JsOverlay
    final static void save(Settings settings) {
        Storage storage = WebStorageWindow.of(window).localStorage;
        if (storage != null) {
            LocalStorage ls = new LocalStorage();
            ls.name = settings.getName();
            ls.numbers = settings.getNumbers();
            ls.timeout = settings.getTimeout();
            ls.penalty = settings.getPenalty();
            ls.retries = settings.getRetries();
            ls.autoDice = settings.isAutoDice();
            ls.level = settings.getLevel().name().toLowerCase();
            storage.setItem("schlawiner.settings", JSON.stringify(ls));
        }
    }
}
