package org.jboss.schlawiner.client.game;

import elemental2.core.JsArray;
import elemental2.dom.HTMLElement;
import jsinterop.base.JsPropertyMap;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.client.game.Raphael.Path;

import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.schlawiner.client.resources.CSS.countdown;

@SuppressWarnings("unchecked")
class CountdownElement implements IsElement<HTMLElement> {

    private static final String COLOR = "rgba(153, 153, 153, .25)";
    private static final String WARNING = "rgba(251, 102, 102, .25)";
    private static final double HEIGHT = 150;
    private static final double RAD = 66;
    private static final double WIDTH = 150;

    private final HTMLElement root;
    private final Raphael paper;
    private Raphael.Element arc;
    private Raphael.Element number;

    private double threshold;
    private double total;

    CountdownElement() {
        this.root = div().css(countdown).asElement();
        this.paper = new Raphael(root, WIDTH, HEIGHT);

        paper.customAttributes.arc = (value, total) -> {
            double cx = WIDTH / 2;
            double cy = HEIGHT / 2;
            double alpha = 360 / total * value;
            double a = (90 - alpha) * Math.PI / 180;
            double x = cx + RAD * Math.cos(a);
            double y = cy - RAD * Math.sin(a);

            Path path = new Path();
            path.path = new JsArray<>();
            path.path.push(new JsArray<>("M", cx, cy - RAD));
            if (value == total) {
                path.path.push(new JsArray<>("A", RAD, RAD, 0.0, 1.0, 1.0, cx - 0.01, cy - RAD));
            } else {
                path.path.push(new JsArray<>("A", RAD, RAD, 0.0, (alpha > 180) ? 1.0 : 0.0, 1.0, x, y));
            }
            return path;
        };
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    void reset(double timeout) {
        total = timeout;
        threshold = timeout * (80 / 100.0);

        if (arc == null) {
            arc = paper.path();
        }
        arc.attr(JsPropertyMap.of("arc", JsArray.of(0.0, timeout),
            "stroke", COLOR,
            "stroke-width", 15.0));
    }

    void tick(double value) {
        if (arc != null) {
            String color = value < threshold ? COLOR : WARNING;
            arc.attr(JsPropertyMap.of("color", color));
            arc.animate(JsPropertyMap.of("arc", JsArray.of(value, total)), 750, "bounce");
        }
    }

    void number(double n) {
        if (number == null) {
            number = paper.text(WIDTH / 2, HEIGHT / 2, " ");
            number.attr(JsPropertyMap.of("font-family", "Architects Daughter",
                "font-size", "48px",
                "fill", "rgb(236, 236, 236)"));
        }
        number.attr(JsPropertyMap.of("text", n));
    }
}
