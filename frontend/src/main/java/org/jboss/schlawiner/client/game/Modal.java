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
public class Modal {

    @JsConstructor
    Modal(ModalOptions options) {
    }

    native void open();

    native void close();

    native void destroy();

    @JsOverlay
    final void setContent(HTMLElement element) {
        setContent(div().add(element).asElement().innerHTML);
    }

    native void setContent(String content);

    @JsOverlay
    final void setFooterContent(HTMLElement element) {
        setFooterContent(element.innerHTML);
    }

    native void setFooterContent(String content);

    @JsOverlay
    final void addFooterBtn(HTMLElement button, String cssClass, Callback callback) {
        addFooterBtn(button.innerHTML, cssClass, callback);
    }

    native void addFooterBtn(String content, String cssClass, Callback callback);

    @JsFunction
    @FunctionalInterface
    interface Callback {

        void call();
    }


    @JsFunction
    @FunctionalInterface
    interface Feedback {

        boolean call();
    }


    @JsType(isNative = true, namespace = GLOBAL, name = OBJECT)
    public static class ModalOptions {

        boolean footer;
        boolean stickyFooter;
        JsArray<State> cssClass;
        String closeLabel;
        JsArray<String> closeMethods;
        Callback beforeOpen;
        Callback onOpen;
        Feedback beforeClose;
        Callback onClose;
    }
}
