package org.jboss.schlawiner.engine.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    private Dice dice;

    @BeforeEach
    void setUp() {
        dice = new Dice(1, 2, 3);
    }

    @Test
    void calculate() {
        assertEquals(16, Calculator.calculate("10+2*3", dice));
        assertEquals(36, Calculator.calculate("(10+2)*3", dice));
        assertEquals(16, Calculator.calculate("10 + 2 * 3", dice));
        assertEquals(36, Calculator.calculate("(10 + 2) * 3", dice));
        assertEquals(36, Calculator.calculate("((10 + 2) * (3))", dice));
    }

    @Test
    void noExpression() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("foo", dice));
    }

    @Test
    void wrongExpression() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("10 + 3 *", dice));
    }

    @Test
    void wrongOperator() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("10 & 2 % 3", dice));
    }

    @Test
    void noInteger() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("10 + 2 / 3", dice));
    }

    @Test
    void wrongDiceNumbers() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("4 + 5 + 6", dice));
    }

    @Test
    void wrongMultipliers() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("1 + 2 + 3000", dice));
    }
}
