package org.jboss.schlawiner.client.game;

import org.gwtproject.event.shared.Event;

public class ChatMessageEvent extends Event<ChatMessageEvent.ChatMessageHandler> {

    public static final Type<ChatMessageHandler> TYPE = new Type<ChatMessageHandler>();


    private final String message;

    public ChatMessageEvent(String message) {
        this.message = message;
    }

    @Override
    public Type<ChatMessageHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ChatMessageHandler handler) {
        handler.onMessage(this);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ChatMessageEvent(" + message + ")";
    }


    public interface ChatMessageHandler {

        public void onMessage(ChatMessageEvent event);
    }
}
