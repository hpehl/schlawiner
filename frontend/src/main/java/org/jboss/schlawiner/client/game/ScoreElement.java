package org.jboss.schlawiner.client.game;

import elemental2.dom.Element;
import elemental2.dom.HTMLTableElement;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.client.resources.Ids;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.score.Score;
import org.jboss.schlawiner.engine.score.Scoreboard;

import static elemental2.dom.DomGlobal.document;
import static org.jboss.gwt.elemento.core.Elements.asHtmlElement;
import static org.jboss.gwt.elemento.core.Elements.htmlElements;
import static org.jboss.gwt.elemento.core.Elements.span;
import static org.jboss.gwt.elemento.core.Elements.stream;
import static org.jboss.schlawiner.client.resources.CSS.highlight;

abstract class ScoreElement implements IsElement<HTMLTableElement> {

    void highlight(Player player, int numberIndex) {
        clearTags("TH", "TD");
        Element element = document.getElementById(scoreId(player, numberIndex));
        if (element != null) {
            element.classList.add(highlight);
        }
    }

    void clear() {
        clearTags("TH", "TD");
    }

    private void clearTags(String... tags) {
        for (String tag : tags) {
            stream(asElement().getElementsByTagName(tag))
                .filter(htmlElements())
                .map(asHtmlElement())
                .forEach(e -> e.classList.remove(highlight));
        }
    }

    void setScore(Scoreboard scoreboard, Player player, int numberIndex, Score score) {
        Element element = document.getElementById(scoreId(player, numberIndex));
        if (element != null) {
            Elements.removeChildrenFrom(element);
            element.appendChild(span()
                .title(score.getTerm())
                .textContent(String.valueOf(score.getDifference()))
                .asElement());
        }
        element = document.getElementById(sumId(player));
        if (element != null) {
            element.textContent = String.valueOf(scoreboard.getSummedScore(player));
        }
    }

    String playerId(Player player) {
        return Ids.build(prefix(), "player", player.getId());
    }

    String numberId(int numberIndex) {
        return Ids.build(prefix(), "number", String.valueOf(numberIndex));
    }

    String scoreId(Player player, int numberIndex) {
        return Ids.build(prefix(), "score", player.getId(), String.valueOf(numberIndex));
    }

    String sumId(Player player) {
        return Ids.build(prefix(), "sum", player.getId());
    }

    abstract String prefix();
}
