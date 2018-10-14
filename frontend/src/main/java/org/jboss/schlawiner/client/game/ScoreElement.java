package org.jboss.schlawiner.client.game;

import elemental2.dom.Element;
import elemental2.dom.HTMLTableElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.client.resources.Ids;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.score.Score;

import static elemental2.dom.DomGlobal.document;
import static org.jboss.gwt.elemento.core.Elements.asHtmlElement;
import static org.jboss.gwt.elemento.core.Elements.htmlElements;
import static org.jboss.gwt.elemento.core.Elements.span;
import static org.jboss.gwt.elemento.core.Elements.stream;
import static org.jboss.schlawiner.client.resources.CSS.highlight;

abstract class ScoreElement implements IsElement<HTMLTableElement> {

    void highlight(Player player) {
        clear("TH", "TD");
        Element element = document.getElementById(playerId(player));
        if (element != null) {
            element.classList.add(highlight);
        }
    }

    void highlight(Player player, int numberIndex) {
        clear("TH", "TD");
        Element element = document.getElementById(scoreId(player, numberIndex));
        if (element != null) {
            element.classList.add(highlight);
        }
    }

    private void clear(String... tags) {
        for (String tag : tags) {
            stream(asElement().getElementsByTagName(tag))
                .filter(htmlElements())
                .map(asHtmlElement())
                .forEach(e -> e.classList.remove(highlight));
        }
    }

    void setScore(Player player, int numberIndex, Score score) {
        Element element = document.getElementById(scoreId(player, numberIndex));
        if (element != null) {
            element.appendChild(span()
                .title(score.getTerm())
                .textContent(String.valueOf(score.getDifference()))
                .asElement());
        }
    }

    String playerId(Player player) {
        return Ids.build("player", player.getName());
    }

    String numberId(int numberIndex) {
        return Ids.build("number", String.valueOf(numberIndex));
    }

    String scoreId(Player player, int numberIndex) {
        return Ids.build("score", player.getName(), String.valueOf(numberIndex));
    }

    String sumId(Player player) {
        return Ids.build("sum", player.getName());
    }
}
