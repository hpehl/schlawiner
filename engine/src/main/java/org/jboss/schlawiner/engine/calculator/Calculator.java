package org.jboss.schlawiner.engine.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.common.base.CharMatcher;
import org.jboss.schlawiner.engine.game.DiceNumberChecker;
import org.jboss.schlawiner.engine.term.Operator;

public class Calculator {

    public int calculate(String term, int[] diceNumbers) throws ArithmeticException {
        DiceNumberChecker checker = new DiceNumberChecker(diceNumbers);
        checker.check(term);

        String[] tokens = split(term);
        String[] rpn = infixToRPN(tokens);
        return evalRpn(rpn);
    }

    private String[] split(String expression) {
        StringBuilder number = new StringBuilder();
        List<String> tokens = new ArrayList<>();
        CharMatcher ops = CharMatcher.anyOf("()+-*/");
        CharMatcher whitespace = CharMatcher.whitespace();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (ops.matches(c) || whitespace.matches(c)) {
                if (number.length() > 0) {
                    tokens.add(number.toString());
                    number = new StringBuilder();
                }
                if (ops.matches(c)) {
                    tokens.add(String.valueOf(c));
                }
            } else {
                number.append(c);
            }
        }
        if (number.length() > 0) {
            tokens.add(number.toString());
        }
        return tokens.toArray(new String[0]);
    }

    private String[] infixToRPN(String[] tokens) {
        ArrayList<String> out = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for (String token : tokens) {
            if (isOperator(token)) {
                while (!stack.empty() && isOperator(stack.peek())) {
                    if (cmpPrecedence(token, stack.peek()) <= 0) {
                        out.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.empty() && !stack.peek().equals("(")) {
                    out.add(stack.pop());
                }
                stack.pop();
            } else {
                // should be a number
                try {
                    //noinspection ResultOfMethodCallIgnored
                    Integer.valueOf(token);
                    out.add(token);
                } catch (NumberFormatException e) {
                    throw new ArithmeticException(token + " is not a number");
                }
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        return out.toArray(new String[0]);
    }

    private boolean isOperator(String token) {
        Operator op = Operator.toOperator(token);
        return op != null;
    }

    private int cmpPrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            throw new ArithmeticException("Invalid tokens: " + token1 + " " + token2);
        }
        //noinspection ConstantConditions
        return Operator.toOperator(token1).precedence() - Operator.toOperator(token2).precedence();
    }

    private int evalRpn(String[] rpn) {
        Stack<String> stack = new Stack<>();

        // For each token
        for (String token : rpn) {
            // If the token is a number push it onto the stack
            if (!isOperator(token)) {
                stack.push(token);
            } else {
                // Pop the two top entries
                if (stack.size() < 2) {
                    throw new ArithmeticException("Invalid expression");
                }
                Integer right;
                String pop = stack.pop();
                try {
                    right = Integer.valueOf(pop);
                } catch (NumberFormatException e) {
                    throw new ArithmeticException("" + pop + " is no valid number");
                }
                Integer left;
                pop = stack.pop();
                try {
                    left = Integer.valueOf(pop);
                } catch (NumberFormatException e) {
                    throw new ArithmeticException("" + pop + " is no valid number");
                }

                // Get the operator
                Integer result = null;
                Operator operator = Operator.toOperator(token);
                if (operator != null) {
                    switch (operator) {
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
                            if (left % right != 0) {
                                throw new ArithmeticException("" + left + " / " + right + " is not an integer result");
                            }
                            result = left / right;
                            break;
                    }
                } else {
                    throw new ArithmeticException("" + token + " is no valid operator");
                }

                // Push result onto stack
                stack.push(String.valueOf(result));
            }
        }
        String pop = stack.pop();
        try {
            return Integer.valueOf(pop);
        } catch (NumberFormatException e) {
            throw new ArithmeticException("No valid expression: " + pop);
        }
    }
}
