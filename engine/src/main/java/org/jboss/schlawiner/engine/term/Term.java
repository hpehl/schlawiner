package org.jboss.schlawiner.engine.term;

import java.util.Iterator;
import java.util.Stack;

import com.google.common.collect.Iterators;

public class Term implements Node {

    private final Operator operator;
    private Node parent;
    private Node left;
    private Node right;


    Term(Operator operator) {
        this.operator = operator;
    }

    public int eval(Integer... values) {
        assign(values);
        return new EvalIterator().eval(this);
    }

    public String print() {
        return new PrintIterator().print(this);
    }

    boolean isComplete() {
        return left != null && right != null;
    }

    Term assign(String name, int value) {
        Variable v = new SearchIterator().search(this, name);
        if (v != null) {
            v.setValue(value);
        }
        return this;
    }

    public Term assign(Integer... values) {
        new AssignValuesIterator().assign(this, values);
        return this;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getLeft() {
        return left;
    }

    @Override
    public void setLeft(Node left) {
        this.left = left;
        this.left.setParent(this);
    }

    @Override
    public Node getRight() {
        return right;
    }

    @Override
    public void setRight(Node right) {
        this.right = right;
        this.right.setParent(this);
    }

    private Operator getOperator() {
        return operator;
    }


    static class EvalIterator {

        int eval(Node node) {
            Stack<Integer> stack = new Stack<>();
            postOrder(node, stack);
            return stack.pop();
        }

        private void postOrder(Node node, Stack<Integer> stack) {
            if (node instanceof Term) {
                postOrder(node.getLeft(), stack);
                postOrder(node.getRight(), stack);
                int right = stack.pop();
                int left = stack.pop();
                int result = 0;
                switch (((Term) node).getOperator()) {
                    case PLUS:
                        result = left + right;
                        break;
                    case MINUS:
                        result = left - right;
                        break;
                    case TIMES:
                        result = left * right;
                        break;
                    case DIVIDED:
                        if (right == 0 || left % right != 0) {
                            throw new ArithmeticException("Illegal division: " + left + " / " + right);
                        }
                        result = left / right;
                        break;
                }
                stack.push(result);
            } else if (node instanceof Variable) {
                Variable v = (Variable) node;
                if (!v.isAssigned()) {
                    throw new ArithmeticException("Variable is not assigned");
                }
                stack.push(v.getValue());
            }
        }
    }


    static class PrintIterator {

        String print(Node node) {
            StringBuilder builder = new StringBuilder();
            inOrder(node, builder);
            return builder.toString();
        }

        private void inOrder(Node node, StringBuilder builder) {
            if (node != null) {
                inOrder(node.getLeft(), builder);
                if (node instanceof Variable) {
                    Variable v = (Variable) node;
                    boolean bracket = needsBracket(v);
                    if (bracket && v == v.getParent().getLeft()) {
                        builder.append("(");
                    }
                    if (v.isAssigned()) {
                        builder.append(v.getValue());
                    } else {
                        builder.append(v.getName());
                    }
                    if (bracket && v == v.getParent().getRight()) {
                        builder.append(")");
                    }
                } else if (node instanceof Term) {
                    Term e = (Term) node;
                    builder.append(" ").append(e.getOperator()).append(" ");
                }
                inOrder(node.getRight(), builder);
            }
        }

        private boolean needsBracket(Node node) {
            if (node.getParent() instanceof Term && node.getParent().getParent() instanceof Term) {
                Term parent = (Term) node.getParent();
                Term grandparent = (Term) parent.getParent();
                return parent.getOperator().precedence() < grandparent.getOperator().precedence();
            }
            return false;
        }
    }


    static class AssignValuesIterator {

        void assign(Term term, Integer... values) {
            Iterator<Integer> iterator = Iterators.forArray(values);
            inOrder(term, iterator);
        }

        private void inOrder(Node node, Iterator<Integer> iterator) {
            if (node != null && iterator.hasNext()) {
                inOrder(node.getLeft(), iterator);
                if (node instanceof Variable) {
                    Variable v = (Variable) node;
                    v.setValue(iterator.next());
                }
                inOrder(node.getRight(), iterator);
            }
        }
    }


    static class SearchIterator {

        Variable search(Term term, String name) {
            Variable[] result = new Variable[1];
            inOrder(term, name, result);
            return result[0];
        }

        private void inOrder(Node node, String name, Variable[] result) {
            if (node != null && result[0] == null) {
                inOrder(node.getLeft(), name, result);
                if (node instanceof Variable) {
                    Variable v = (Variable) node;
                    if (name.equals(v.getName())) {
                        result[0] = v;
                    }
                }
                inOrder(node.getRight(), name, result);
            }
        }
    }
}
