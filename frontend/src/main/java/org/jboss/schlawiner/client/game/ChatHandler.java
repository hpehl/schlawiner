package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import org.jboss.schlawiner.client.Context;

@Handler
public class ChatHandler extends AbstractHandler<Context> {

    @Override
    public void bind() {
        eventBus.addHandler(ChatMessageEvent.TYPE, )
    }
}
