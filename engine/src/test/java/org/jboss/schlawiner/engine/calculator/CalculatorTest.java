package org.jboss.schlawiner.engine.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    private Calculator calculator;
    private int[] diceNumbers;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        diceNumbers = new int[]{1, 2, 3};
    }

    @Test
    void calculate() {
        assertEquals(16, calculator.calculate("10+2*3", diceNumbers));
        assertEquals(36, calculator.calculate("(10+2)*3", diceNumbers));
        assertEquals(16, calculator.calculate("10 + 2 * 3", diceNumbers));
        assertEquals(36, calculator.calculate("(10 + 2) * 3", diceNumbers));
        assertEquals(36, calculator.calculate("((10 + 2) * (3))", diceNumbers));
    }

    @Test
    void noExpression() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate("foo", diceNumbers));
    }

    @Test
    void wrongExpression() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate("10 + 3 *", diceNumbers));
    }

    @Test
    void wrongOperator() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate("10 & 2 % 3", diceNumbers));
    }

    @Test
    void noInteger() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate("10 + 2 / 3", diceNumbers));
    }

    @Test
    void wrongDiceNumbers() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate("4 + 5 + 6", diceNumbers));
    }

    @Test
    void wrongMultipliers() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate("1 + 2 + 3000", diceNumbers));
    }
}
