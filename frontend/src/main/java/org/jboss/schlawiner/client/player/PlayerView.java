package org.jboss.schlawiner.client.player;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.gwt.elemento.template.DataElement;
import org.jboss.gwt.elemento.template.Templated;

@Templated
abstract class PlayerView implements IsElement<HTMLElement> {

    static PlayerView create() {
        return new Templated_PlayerView();
    }

    @DataElement HTMLElement tbody;
    @DataElement HTMLInputElement addPlayer;
    @DataElement HTMLElement startGame;
}
