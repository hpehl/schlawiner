package org.jboss.schlawiner.client.resources;

public enum FontAwesomeStyle {
    solid("s"), regular("r"), light("l"), brands("b");

    private final String style;

    FontAwesomeStyle(String style) {
        this.style = style;
    }

    public String style() {
        return style;
    }
}
