package org.jboss.schlawiner.client.game;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "org.jboss.schlawiner")
class Countdown {

    @JsConstructor
    @SuppressWarnings("unused")
    Countdown(HTMLElement element, int width, int height, int rad) {
    }

    native void reset(int total);

    native boolean tick();

    native void number(int number);
}
