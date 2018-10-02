package org.jboss.schlawiner.engine.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jboss.schlawiner.engine.term.Operator;
import org.jboss.schlawiner.engine.term.Term;
import org.jboss.schlawiner.engine.term.TermBuilder;

public class TermAlgorithm extends AbstractAlgorithm {

    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    private final List<Term> terms;

    TermAlgorithm() {
        super();
        terms = new ArrayList<>();

        // a + b + c
        terms.add(new TermBuilder().op(Operator.PLUS).op(Operator.PLUS).var(A).var(B).var(C).build());
        // a - b - c
        terms.add(new TermBuilder().op(Operator.MINUS).op(Operator.MINUS).var(A).var(B).var(C).build());
        // a * b * c
        terms.add(new TermBuilder().op(Operator.TIMES).op(Operator.TIMES).var(A).var(B).var(C).build());
        // a / b / c
        terms.add(new TermBuilder().op(Operator.DIVIDED).op(Operator.DIVIDED).var(A).var(B).var(C).build());
        // a + b - c
        terms.add(new TermBuilder().op(Operator.MINUS).op(Operator.PLUS).var(A).var(B).var(C).build());
        // a * b / c
        terms.add(new TermBuilder().op(Operator.DIVIDED).op(Operator.TIMES).var(A).var(B).var(C).build());
        // a * b + c
        terms.add(new TermBuilder().op(Operator.PLUS).op(Operator.TIMES).var(A).var(B).var(C).build());
        // (a + b) * c
        terms.add(new TermBuilder().op(Operator.TIMES).op(Operator.PLUS).var(A).var(B).var(C).build());
        // a * b - c
        terms.add(new TermBuilder().op(Operator.MINUS).op(Operator.TIMES).var(A).var(B).var(C).build());
        // a - b * c
        terms.add(new TermBuilder().op(Operator.MINUS).var(A).op(Operator.TIMES).var(B).var(C).build());
        // (a - b) * c
        terms.add(new TermBuilder().op(Operator.TIMES).op(Operator.MINUS).var(A).var(B).var(C).build());
        // a / b + c
        terms.add(new TermBuilder().op(Operator.PLUS).op(Operator.DIVIDED).var(A).var(B).var(C).build());
        // (a + b) / c
        terms.add(new TermBuilder().op(Operator.DIVIDED).op(Operator.PLUS).var(A).var(B).var(C).build());
        // a / (b + c)
        terms.add(new TermBuilder().op(Operator.DIVIDED).var(A).op(Operator.PLUS).var(B).var(C).build());
        // a / b - c
        terms.add(new TermBuilder().op(Operator.MINUS).op(Operator.DIVIDED).var(A).var(B).var(C).build());
        // a - b / c
        terms.add(new TermBuilder().op(Operator.MINUS).var(A).op(Operator.DIVIDED).var(B).var(C).build());
        // (a - b) / c
        terms.add(new TermBuilder().op(Operator.DIVIDED).op(Operator.MINUS).var(A).var(B).var(C).build());
        // a / (b - c)
        terms.add(new TermBuilder().op(Operator.DIVIDED).var(A).op(Operator.MINUS).var(B).var(C).build());
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void computePermutation(int a, int b, int c, int target, Solutions solutions) {
        Iterator<Term> iter = terms.iterator();

        // a + b + c
        Term term = iter.next();
        add(term.assign(a, b, c), solutions);

        // a - b - c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(c, a, b), solutions);
        }

        // a * b * c
        term = iter.next();
        add(term.assign(a, b, c), solutions);

        // a / b / c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(c, a, b), solutions);
        }

        // a + b - c
        term = iter.next();
        add(term.assign(b, c, a), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(a, b, c), solutions);
        }

        // a * b / c
        term = iter.next();
        add(term.assign(b, c, a), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(a, b, c), solutions);
        }

        // a * b + c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, c, a), solutions);
        }

        // (a + b) * c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, c, a), solutions);
        }

        // a * b - c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, c, a), solutions);
        }

        // a - b * c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(c, a, b), solutions);
        }

        // (a - b) * c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(a, c, b), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // a / b + c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(a, c, b), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // (a + b) / c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, c, a), solutions);
        }

        // a / (b + c)
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(c, a, b), solutions);
        }

        // a / b - c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, a, a), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // a - b / c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, a, a), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // (a - b) / c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, a, a), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // a / (b - c)
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, a, a), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(c, b, a), solutions);
        }
    }

    private void add(Term term, Solutions solutions) {
        try {
            solutions.add(new Solution(term.print(), term.eval()));
        } catch (ArithmeticException ignore) {
            // no valid result
        }
    }

    @Override
    public String getName() {
        return "Algorithm based on variable terms";
    }
}
