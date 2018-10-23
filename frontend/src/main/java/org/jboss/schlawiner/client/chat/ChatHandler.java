package org.jboss.schlawiner.client.chat;

import java.util.Date;

import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import org.jboss.schlawiner.client.Context;

import static elemental2.dom.DomGlobal.console;

@Handler
public class ChatHandler extends AbstractHandler<Context> {

    @Override
    public void bind() {
        eventBus.addHandler(ChatMessageEvent.TYPE, e -> sendMessage(e.getPlayer(), e.getMessage(), e.getTime()));
    }

    private void sendMessage(String player, String message, Date time) {
        console.log("Received message from " + player + ": " + message + " @ " + time);
        // TODO send message to server
    }
}
