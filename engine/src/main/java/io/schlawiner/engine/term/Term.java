/*
 *  Copyright 2023 Harald Pehl
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.schlawiner.engine.term;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import static io.schlawiner.engine.util.Iterators.forArray;

public class Term implements Node {

    private final Operator operator;
    private Node parent;
    private Node left;
    private Node right;

    Term(final Operator operator) {
        this.operator = operator;
    }

    public int eval(final Integer... values) {
        assign(values);
        return new EvalIterator().eval(this);
    }

    public String print() {
        return new PrintIterator().print(this);
    }

    boolean isComplete() {
        return left != null && right != null;
    }

    Term assign(final String name, final int value) {
        final Variable v = new VariableIterator().search(this, name);
        if (v != null) {
            v.setValue(value);
        }
        return this;
    }

    public Term assign(final Integer... values) {
        new AssignValuesIterator().assign(this, values);
        return this;
    }

    public int[] getValues() {
        return new ValuesIterator().values(this);
    }

    public List<Operator> getOperators() {
        return new OperatorsIterator().operators(this);
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
        return left;
    }

    @Override
    public void setLeft(final Node left) {
        this.left = left;
        this.left.setParent(this);
    }

    @Override
    public Node getRight() {
        return right;
    }

    @Override
    public void setRight(final Node right) {
        this.right = right;
        this.right.setParent(this);
    }

    // ------------------------------------------------------ iterators

    static class EvalIterator {

        int eval(final Node node) {
            final Stack<Integer> stack = new Stack<>();
            postOrder(node, stack);
            return stack.pop();
        }

        private void postOrder(final Node node, final Stack<Integer> stack) {
            if (node instanceof final Term t) {
                postOrder(node.getLeft(), stack);
                postOrder(node.getRight(), stack);
                final int right = stack.pop();
                final int left = stack.pop();
                final int result = switch (t.operator) {
                    case PLUS -> left + right;
                    case MINUS -> left - right;
                    case TIMES -> left * right;
                    case DIVIDED -> {
                        if (right == 0 || left % right != 0) {
                            throw new ArithmeticException("Illegal division: " + left + " / " + right);
                        }
                        yield left / right;
                    }
                };
                stack.push(result);
            } else if (node instanceof final Variable v) {
                if (!v.isAssigned()) {
                    throw new ArithmeticException("Variable is not assigned");
                }
                stack.push(v.getValue());
            }
        }
    }

    static class PrintIterator {

        String print(final Node node) {
            final StringBuilder builder = new StringBuilder();
            inOrder(node, builder);
            return builder.toString();
        }

        private void inOrder(final Node node, final StringBuilder builder) {
            if (node != null) {
                inOrder(node.getLeft(), builder);
                if (node instanceof final Variable v) {
                    final boolean bracket = needsBracket(v);
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
                } else if (node instanceof final Term e) {
                    builder.append(" ").append(e.operator).append(" ");
                }
                inOrder(node.getRight(), builder);
            }
        }

        private boolean needsBracket(final Node node) {
            if (node.getParent() instanceof final Term parent && node.getParent().getParent() instanceof Term) {
                final Term grandparent = (Term) parent.getParent();
                return parent.operator.precedence() < grandparent.operator.precedence();
            }
            return false;
        }
    }

    static class AssignValuesIterator {

        void assign(final Term term, final Integer... values) {
            final Iterator<Integer> iterator = forArray(values);
            inOrder(term, iterator);
        }

        private void inOrder(final Node node, final Iterator<Integer> iterator) {
            if (node != null && iterator.hasNext()) {
                inOrder(node.getLeft(), iterator);
                if (node instanceof final Variable v) {
                    v.setValue(iterator.next());
                }
                inOrder(node.getRight(), iterator);
            }
        }
    }

    static class ValuesIterator {

        int[] values(final Term term) {
            final List<Integer> numbers = new ArrayList<>();
            inOrder(term, numbers);
            return numbers.stream().mapToInt(Integer::intValue).toArray();
        }

        private void inOrder(final Node node, final List<Integer> numbers) {
            if (node != null) {
                inOrder(node.getLeft(), numbers);
                if (node instanceof final Variable v) {
                    if (v.getValue() != Variable.DEFAULT_VALUE) {
                        numbers.add(v.getValue());
                    }
                }
                inOrder(node.getRight(), numbers);
            }
        }
    }

    static class OperatorsIterator {
        List<Operator> operators(final Term term) {
            final List<Operator> operators = new ArrayList<>();
            inOrder(term, operators);
            return operators;
        }

        private void inOrder(final Node node, final List<Operator> operators) {
            if (node != null) {
                inOrder(node.getLeft(), operators);
                if (node instanceof final Term t) {
                    operators.add(t.operator);
                }
                inOrder(node.getRight(), operators);
            }
        }
    }

    static class VariableIterator {

        Variable search(final Term term, final String name) {
            final Variable[] result = new Variable[1];
            inOrder(term, name, result);
            return result[0];
        }

        private void inOrder(final Node node, final String name, final Variable[] result) {
            if (node != null && result[0] == null) {
                inOrder(node.getLeft(), name, result);
                if (node instanceof final Variable v) {
                    if (name.equals(v.getName())) {
                        result[0] = v;
                    }
                }
                inOrder(node.getRight(), name, result);
            }
        }
    }
}
