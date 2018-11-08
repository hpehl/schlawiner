package org.jboss.schlawiner.client.grpc;

import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "grpc.web")
public class Error {

    public native int getCode();

    public native String getMessage();
}
