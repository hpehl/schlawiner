package org.jboss.schlawiner.client;

import elemental2.webstorage.Storage;
import elemental2.webstorage.WebStorageWindow;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import org.jboss.schlawiner.client.resources.Ids;
import org.jboss.schlawiner.engine.game.Level;
import org.jboss.schlawiner.engine.game.Settings;

import static elemental2.core.Global.JSON;
import static elemental2.dom.DomGlobal.window;
import static jsinterop.annotations.JsPackage.GLOBAL;

public class LocalStorage {

    public static Settings loadSettings() {
        Settings settings = new Settings();
        Storage storage = WebStorageWindow.of(window).localStorage;
        if (storage != null) {
            String item = storage.getItem(Ids.SETTINGS_STORAGE);
            if (item != null) {
                LocalStorage.JsSettings jss = Js.cast(JSON.parse(item));
                settings.setName(jss.name);
                settings.setNumbers(jss.numbers);
                settings.setTimeout(jss.timeout);
                settings.setPenalty(jss.penalty);
                settings.setRetries(jss.retries);
                settings.setAutoDice(jss.autoDice);
                settings.setLevel(Level.valueOf(jss.level.toUpperCase()));
            }
        }
        return settings;
    }

    public static void saveSettings(Settings settings) {
        Storage storage = WebStorageWindow.of(window).localStorage;
        if (storage != null) {
            LocalStorage.JsSettings jss = new LocalStorage.JsSettings();
            jss.name = settings.getName();
            jss.numbers = settings.getNumbers();
            jss.timeout = settings.getTimeout();
            jss.penalty = settings.getPenalty();
            jss.retries = settings.getRetries();
            jss.autoDice = settings.isAutoDice();
            jss.level = settings.getLevel().name().toLowerCase();
            storage.setItem(Ids.SETTINGS_STORAGE, JSON.stringify(jss));
        }
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "Object")
    static class JsSettings {

        String name;
        int numbers;
        int timeout;
        int penalty;
        int retries;
        boolean autoDice;
        String level;

    }
}
