package org.jboss.schlawiner.client.grpc;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "grpc.web")
public interface ClientReadableStream<T> {

    ClientReadableStream<T> on(String type, Callback callback);

    void cancel();

    @JsFunction
    @FunctionalInterface
    interface Callback {

        void call(Object arg);
    }
}
