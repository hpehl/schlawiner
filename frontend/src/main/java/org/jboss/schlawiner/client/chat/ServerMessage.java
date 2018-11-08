package org.jboss.schlawiner.client.chat;

import jsinterop.annotations.JsType;
import org.jboss.schlawiner.client.grpc.Timestamp;

@JsType(isNative = true, namespace = "proto.chat")
class ServerMessage {

    native ClientMessage getMessage();

    native void setMessage(ClientMessage message);

    native boolean hasMessage();

    native Timestamp getTimestamp();

    native void setTimestamp(Timestamp timestamp);

    native boolean hasTimestamp();
}
