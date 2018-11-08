package org.jboss.schlawiner.client.chat;

import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "proto.chat")
class ClientMessage {

    native String getMessage();

    native void setMessage(String message);

    native String getPlayer();

    native void setPlayer(String player);
}
