package org.jboss.schlawiner.engine.term;

public enum Operator {
    PLUS(0) {
        @Override
        public String toString() {
            return "+";
        }
    },
    MINUS(0) {
        @Override
        public String toString() {
            return "-";
        }
    },
    TIMES(5) {
        @Override
        public String toString() {
            return "*";
        }
    },
    DIVIDED(5) {
        @Override
        public String toString() {
            return "/";
        }
    };
    private final int precedence;


    Operator(final int precedence) {
        this.precedence = precedence;
    }

    public static Operator toOperator(String token) {
        if ("+".equals(token)) {
            return PLUS;
        } else if ("-".equals(token)) {
            return MINUS;
        } else if ("*".equals(token)) {
            return TIMES;
        } else if ("/".equals(token)) {
            return DIVIDED;
        }
        return null;
    }

    public int precedence() {
        return this.precedence;
    }
}
