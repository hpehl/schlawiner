package org.jboss.schlawiner.client;

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
public class Modal {

    @JsConstructor
    @SuppressWarnings("unused")
    public Modal(ModalOptions options) {
    }

    public native void open();

    public native void destroy();

    @JsOverlay
    public final void setContent(HTMLElement element) {
        setContent(div().add(element).asElement().innerHTML);
    }

    public native void setContent(String content);

    @JsFunction
    @FunctionalInterface
    public interface Callback {

        void call();
    }


    @JsType(isNative = true, namespace = GLOBAL, name = OBJECT)
    public static class ModalOptions {

        public JsArray<String> closeMethods;
        public Callback onClose;
    }
}
