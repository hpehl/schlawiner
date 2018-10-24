package org.jboss.schlawiner.client.chat;

import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import org.jboss.schlawiner.client.Context;

import static elemental2.dom.DomGlobal.console;

@Handler
public class ChatHandler extends AbstractHandler<Context> {

    @Override
    public void bind() {
        eventBus.addHandler(ChatMessageEvent.TYPE, e -> sendMessage(e.getMessage()));
    }

    private void sendMessage(ChatMessage message) {
        console.log("Received message from " + message.getPlayer()
            .getName() + ": " + message.getMessage() + " @ " + message.getTime());
        // TODO send message to server
    }
}
