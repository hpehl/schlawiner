package org.jboss.schlawiner.client.player;

import com.github.nalukit.nalu.client.component.IsComponent;

public interface PlayerController extends IsComponent.Controller {

    void addPlayer(String name);

    void editPlayer(String id, String name);

    void editPlayer(String id, boolean human);

    void removePlayer(String id);

    void startGame();
}
