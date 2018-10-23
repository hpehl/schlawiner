package org.jboss.schlawiner.client.chat;

import org.gwtproject.event.shared.Event;

public class ChatMessageEvent extends Event<ChatMessageEvent.ChatMessageHandler> {

    public static final Type<ChatMessageHandler> TYPE = new Type<ChatMessageHandler>();


    private final ChatMessage message;

    public ChatMessageEvent(ChatMessage message) {
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

    public ChatMessage getMessage() {
        return message;
    }

    public interface ChatMessageHandler {

        public void onMessage(ChatMessageEvent event);
    }
}
