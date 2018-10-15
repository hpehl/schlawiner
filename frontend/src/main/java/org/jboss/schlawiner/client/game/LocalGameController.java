package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.component.IsComponent;

public interface LocalGameController extends IsComponent.Controller {

    void dice();

    void skip();

    void restart();

    void back();

    void setTerm(String term, boolean updateInput);

    void solve();
}
