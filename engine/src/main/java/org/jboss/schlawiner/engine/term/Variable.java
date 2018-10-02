package org.jboss.schlawiner.engine.term;

class Variable implements Node {

    static final String DEFAULT_NAME = "x";
    static final int DEFAULT_VALUE = Integer.MIN_VALUE;

    private final String name;
    private int value;
    private boolean assigned;
    private Node parent;


    Variable(String name, int value) {
        this.name = name;
        this.value = value;
        this.assigned = value != DEFAULT_VALUE;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(final Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getLeft() {
        return null;
    }

    @Override
    public void setLeft(final Node left) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node getRight() {
        return null;
    }

    @Override
    public void setRight(final Node right) {
        throw new UnsupportedOperationException();
    }

    String getName() {
        return name;
    }

    int getValue() {
        return value;
    }

    void setValue(final int value) {
        this.value = value;
        this.assigned = value != DEFAULT_VALUE;
    }

    boolean isAssigned() {
        return assigned;
    }
}
