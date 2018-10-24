package org.jboss.schlawiner.client.chat;

import java.util.Date;

import org.jboss.schlawiner.engine.game.Player;

public class ChatMessage {

    private final Player player;
    private final String message;
    private final Date time;

    ChatMessage(Player player, String message) {
        this.player = player;
        this.message = message;
        this.time = new Date();
    }

    @Override
    public String toString() {
        return "ChatMessage(" + player + ": " + message + " @ " + time + ")";
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }
}
