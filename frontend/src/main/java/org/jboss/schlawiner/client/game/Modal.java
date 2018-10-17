package org.jboss.schlawiner.client.game;

import elemental2.core.JsArray;
import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;
import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.schlawiner.client.resources.UIConstants.OBJECT;

@JsType(isNative = true, namespace = "tingle", name = "modal")
class Modal {

    @JsConstructor
    Modal(ModalOptions options) {
    }

    native void open();

    native void destroy();

    @JsOverlay
    final void setContent(HTMLElement element) {
        setContent(div().add(element).asElement().innerHTML);
    }

    native void setContent(String content);

    @JsFunction
    @FunctionalInterface
    interface Callback {

        void call();
    }


    @JsType(isNative = true, namespace = GLOBAL, name = OBJECT)
    static class ModalOptions {

        JsArray<String> closeMethods;
        Callback onClose;
    }
}
