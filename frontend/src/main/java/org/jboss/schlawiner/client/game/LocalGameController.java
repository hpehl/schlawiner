package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.component.IsComponent;

public interface LocalGameController extends IsComponent.Controller {

    void dice();

    void retry();

    void skip();

    void timeout();

    void restart();

    void back();

    void setTerm(String term, boolean updateInput);

    void calculate();
}
