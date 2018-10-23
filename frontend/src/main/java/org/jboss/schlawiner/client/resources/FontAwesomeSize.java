package org.jboss.schlawiner.client.resources;

public enum FontAwesomeSize {

    xs("xs"), sm("sm"), large("lg"), x2("2x"), x3("3x"), x5("5x"), x7("7x");

    private final String size;

    FontAwesomeSize(String size) {
        this.size = size;
    }

    public String size() {
        return size;
    }
}
