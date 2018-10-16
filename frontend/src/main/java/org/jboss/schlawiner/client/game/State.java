package org.jboss.schlawiner.client.game;

public interface State {

    enum Local {
        PLAYERS,
        NEXT,
        ENTER_TERM,
        COMPUTER,
        MESSAGE,
        FINISHED
    }


    enum Network {
        PLAYERS,
    }
}
