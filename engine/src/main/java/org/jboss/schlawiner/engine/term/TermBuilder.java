package org.jboss.schlawiner.engine.term;

import java.util.Stack;

public class TermBuilder {

    private Term current;
    private int namingIndex;
    private Stack<Term> terms;


    public TermBuilder() {
        this.namingIndex = 0;
        this.terms = new Stack<>();
    }

    public TermBuilder op(Operator op) {
        Term e = new Term(op);
        if (!terms.isEmpty()) {
            add(terms.peek(), e);
        }
        terms.push(e);
        return this;
    }

    public TermBuilder var(String name) {
        return assign(name, Variable.DEFAULT_VALUE);
    }

    TermBuilder val(int value) {
        return assign(Variable.DEFAULT_NAME + (namingIndex++), value);
    }

    private TermBuilder assign(String name, int value) {
        if (terms.isEmpty()) {
            throw new IllegalStateException(
                    "No expression available when calling TermBuilder.assign(" + name + ", " + value + ")");
        }

        Variable v = new Variable(name, value);
        Term e = terms.peek();
        add(e, v);
        while (e != null && e.isComplete()) {
            current = terms.pop();
            e = terms.isEmpty() ? null : terms.peek();
        }
        return this;
    }

    private void add(Term parent, Node child) {
        if (parent.getLeft() == null) {
            parent.setLeft(child);
        } else if (parent.getRight() == null) {
            parent.setRight(child);
        }
    }

    public Term build() {
        if (!terms.isEmpty()) {
            throw new IllegalStateException("Unbalanced calls");
        }
        return current;
    }
}
