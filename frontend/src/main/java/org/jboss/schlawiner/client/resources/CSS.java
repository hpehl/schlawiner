package org.jboss.schlawiner.client.resources;

import static org.jboss.schlawiner.client.resources.FontAwesomeStyle.brands;
import static org.jboss.schlawiner.client.resources.FontAwesomeStyle.light;
import static org.jboss.schlawiner.client.resources.FontAwesomeStyle.regular;
import static org.jboss.schlawiner.client.resources.FontAwesomeStyle.solid;

public interface CSS {

    String about = "about";
    String action = "action";
    String actions = "actions";
    String alignSelfCenter = "align-self-center";
    String back = "back";
    String bottom = "bottom";
    String chat = "chat";
    String chatContainer = "chat-container";
    String chatDisabled = "chat-disabled";
    String chatHistory = "chat-history";
    String chatInput = "chat-input";
    String chatSlider = "chat-slider";
    String clickable = "clickable";
    String control = "control";
    String countdown = "countdown";
    String countdownContainer = "countdown-container";
    String cube = "cube";
    String cubeContainer = "cube-container";
    String dice = "dice";
    String disabled = "disabled";
    String front = "front";
    String highlight = "highlight";
    String input = "input";
    String left = "left";
    String line = "line";
    String links = "links";
    String mb0 = "mb-0";
    String numpad = "numpad";
    String overflowY = "overflow-y";
    String page = "page";
    String pageWithOpenChat = "page-with-open-chat";
    String right = "right";
    String row = "row";
    String selected = "selected";
    String score = "score";
    String showFront = "show-front";
    String solution = "solution";
    String text = "text";
    String textLeft = "text-left";
    String textTruncate = "text-truncate";
    String tingleContentWrapper = "tingle-content-wrapper";
    String top = "top";
    String user = "user";

    static String fas(String name) {
        return fa(name, solid, null);
    }

    static String fas(String name, FontAwesomeSize size) {
        return fa(name, solid, size);
    }

    static String far(String name) {
        return fa(name, regular, null);
    }

    static String far(String name, FontAwesomeSize size) {
        return fa(name, regular, size);
    }

    static String fal(String name) {
        return fa(name, light, null);
    }

    static String fal(String name, FontAwesomeSize size) {
        return fa(name, light, size);
    }

    static String fab(String name) {
        return fa(name, brands, null);
    }

    static String fab(String name, FontAwesomeSize size) {
        return fa(name, brands, size);
    }

    /**
     * Builds a FontAwesome icons class.
     *
     * @param name  the name of the FontAwesome icon <strong>w/o</strong> any prefix.
     * @param style the FontAwesome style (solid, regular, light, brands)
     */
    static String fa(String name, FontAwesomeStyle style) {
        return fa(name, style, null);
    }

    /**
     * Builds a FontAwesome icons class.
     *
     * @param name  the name of the FontAwesome icon <strong>w/o</strong> any prefix.
     * @param style the FontAwesome style (solid, regular, light, brands)
     * @param size  the size of the FontAwesome icon
     */
    static String fa(String name, FontAwesomeStyle style, FontAwesomeSize size) {
        String css = "fa" + style.style() + " fa-" + name;
        if (size != null) {
            css += " fa-" + size.size();
        }
        return css;
    }
}
