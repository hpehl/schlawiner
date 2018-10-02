package org.jboss.schlawiner.engine.term;

/** Node of a binary tree */
public interface Node {

    Node getParent();

    void setParent(Node parent);

    Node getLeft();

    void setLeft(Node left);

    Node getRight();

    void setRight(Node right);
}
