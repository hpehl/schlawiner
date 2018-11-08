package org.jboss.schlawiner.client.grpc;

import java.util.Date;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "proto.google.protobuf")
public class Timestamp {

    @JsOverlay
    public final Date asDate() {
        return new Date((long) (getSeconds() * 1000));
    }

    native double getSeconds();
}
