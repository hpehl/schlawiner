package org.jboss.schlawiner.client.player;

import com.github.nalukit.nalu.client.component.IsComponent;
import com.github.nalukit.nalu.client.component.IsComponentCreator;

public interface PlayersController extends IsComponent.Controller, IsComponentCreator<PlayersComponent> {

    void addPlayer(String name);

    void togglePlayer(String id, boolean human);

    void removePlayer(String id);

    void startGame();
}
