package org.jboss.schlawiner.engine.algorithm;

public final class Operations {

    public static Solution add(int a, int b, int c) {
        return new Solution("" + a + " + " + b + " + " + c, a + b + c);
    }

    public static Solution addDivide1(int a, int b, int c) {
        if ((a + b) % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("(" + a + " + " + b + ") / " + c, (a + b) / c);
    }

    public static Solution addDivide2(int a, int b, int c) {
        if (a % (b + c) != 0) {
            return Solution.INVALID;
        }
        return new Solution("" + a + " / (" + b + " + " + c + ")", a / (b + c));
    }

    public static Solution addMultiply(int a, int b, int c) {
        return new Solution("(" + a + " + " + b + ") * " + c, (a + b) * c);
    }

    public static Solution addSubtract(int a, int b, int c) {
        return new Solution("" + a + " + " + b + " - " + c, a + b - c);
    }

    public static Solution divide(int a, int b, int c) {
        if (a % b != 0 || (a / b) % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("" + a + " / " + b + " / " + c, a / b / c);
    }

    public static Solution divideAdd(int a, int b, int c) {
        if (a % b != 0) {
            return Solution.INVALID;
        }
        return new Solution("" + a + " / " + b + " + " + c, a / b + c);
    }

    public static Solution divideSubtract1(int a, int b, int c) {
        if (a % b != 0) {
            return Solution.INVALID;
        }
        return new Solution("" + a + " / " + b + " - " + c, a / b - c);
    }

    public static Solution divideSubtract2(int a, int b, int c) {
        if (b % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("" + a + " - " + b + " / " + c, a - b / c);
    }

    public static Solution multiply(int a, int b, int c) {
        return new Solution("" + a + " * " + b + " * " + c, a * b * c);
    }

    public static Solution multiplyAdd(int a, int b, int c) {
        return new Solution("" + a + " * " + b + " + " + c, a * b + c);
    }

    public static Solution multiplyDivide(int a, int b, int c) {
        if ((a * b) % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("" + a + " * " + b + " / " + c, a * b / c);
    }

    public static Solution multiplySubtract1(int a, int b, int c) {
        return new Solution("" + a + " * " + b + " - " + c, a * b - c);
    }

    public static Solution multiplySubtract2(int a, int b, int c) {
        return new Solution("" + a + " - " + b + " * " + c, a - b * c);
    }

    public static Solution subtract(int a, int b, int c) {
        return new Solution("" + a + " - " + b + " - " + c, a - b - c);
    }

    public static Solution subtractDivide1(int a, int b, int c) {
        if ((a - b) % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("(" + a + " - " + b + ") / " + c, (a - b) / c);
    }

    public static Solution subtractDivide2(int a, int b, int c) {
        if (b - c == 0 || a % (b - c) != 0) {
            return Solution.INVALID;
        }
        return new Solution("" + a + " / (" + b + " - " + c + ")", a / (b - c));
    }

    public static Solution subtractMultiply(int a, int b, int c) {
        return new Solution("(" + a + " - " + b + ") * " + c, (a - b) * c);
    }
}
