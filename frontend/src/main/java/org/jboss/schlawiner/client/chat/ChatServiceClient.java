package org.jboss.schlawiner.client.chat;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsType;
import jsinterop.base.JsPropertyMap;
import org.jboss.schlawiner.client.grpc.Error;

@JsType(isNative = true, namespace = "proto.chat")
public class ChatServiceClient {

/*
    public ChatServiceClient(String hostename) {
    }
*/

    public native void simpleChat(ClientMessage message, JsPropertyMap<String> metadata, MessageCallback callback);

    @JsFunction
    @FunctionalInterface
    public interface MessageCallback {

        void onMessage(Error error, ServerMessage message);
    }
}
