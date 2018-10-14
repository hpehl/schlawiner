package org.jboss.schlawiner.client.game;

import elemental2.core.JsArray;
import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsType;
import jsinterop.base.JsPropertyMap;

import static jsinterop.annotations.JsPackage.GLOBAL;
import static org.jboss.schlawiner.client.resources.UIConstants.OBJECT;

@SuppressWarnings({"WeakerAccess", "unused"})
@JsType(isNative = true, namespace = GLOBAL)
public class Raphael {

    public CustomAttributes customAttributes;

    public Raphael(HTMLElement element, double width, double height) {
    }

    public native Element text(double x, double y, String text);

    public native Element path();


    @JsType(isNative = true, namespace = GLOBAL, name = OBJECT)
    public static class Element {

        public native void animate(JsPropertyMap<Object> params, int millis, String easing);

        public native void attr(JsPropertyMap<Object> params);

        public native void remove();
    }


    @JsType(isNative = true, namespace = GLOBAL, name = OBJECT)
    public static class CustomAttributes {

        public ArcFunction arc;
    }


    @JsType(isNative = true, namespace = GLOBAL, name = OBJECT)
    public static class Path {

        public JsArray<JsArray<Object>> path;
    }


    @JsFunction
    @FunctionalInterface
    public interface ArcFunction {

        Path arc(double value, double total);
    }
}
