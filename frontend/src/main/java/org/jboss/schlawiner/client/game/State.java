package org.jboss.schlawiner.client.game;

public interface State {

    enum Local {
        NEXT,
        ENTER_TERM,
        COMPUTER,
        MODAL,
        FINISHED
    }


    enum Network {
        PLAYERS,
    }
}
