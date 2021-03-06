package org.jboss.schlawiner.engine.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiceValidatorTest {

    private Dice dice;

    @BeforeEach
    void setUp() {
        dice = new Dice(1, 2, 3);
    }

    @Test
    void validateNull() {
        assertThrows(ArithmeticException.class, () -> DiceValidator.validate(dice, null));
    }

    @Test
    void validateEmpty() {
        assertThrows(ArithmeticException.class, () -> DiceValidator.validate(dice, ""));
    }

    @Test
    void validateBlank() {
        assertThrows(ArithmeticException.class, () -> DiceValidator.validate(dice, "       "));
    }

    @Test
    void oneNumber() {
        assertThrows(ArithmeticException.class, () -> DiceValidator.validate(dice, "1"));
    }

    @Test
    void twoNumbers() {
        assertThrows(ArithmeticException.class, () -> DiceValidator.validate(dice, "1 2"));
    }

    @Test
    void fourNumbers() {
        assertThrows(ArithmeticException.class, () -> DiceValidator.validate(dice, "1 2 3 4"));
    }

    @Test
    void wrongNumbers() {
        assertThrows(ArithmeticException.class, () -> DiceValidator.validate(dice, "1 2 4"));
    }

    @Test
    void validate() {
        DiceValidator.validate(dice, "1 2 3");
        DiceValidator.validate(dice, "1 20 300");
    }

    @Test
    void used() {
        assertArrayEquals(new boolean[]{false, false, false}, DiceValidator.used(dice, null));
        assertArrayEquals(new boolean[]{false, false, false}, DiceValidator.used(dice, ""));
        assertArrayEquals(new boolean[]{false, false, false}, DiceValidator.used(dice, "    "));

        assertArrayEquals(new boolean[]{true, false, false}, DiceValidator.used(dice, "1"));
        assertArrayEquals(new boolean[]{false, true, false}, DiceValidator.used(dice, "2"));
        assertArrayEquals(new boolean[]{false, false, true}, DiceValidator.used(dice, "3"));

        assertArrayEquals(new boolean[]{true, false, false}, DiceValidator.used(dice, "4 1"));
        assertArrayEquals(new boolean[]{false, true, false}, DiceValidator.used(dice, "5 2"));
        assertArrayEquals(new boolean[]{false, false, true}, DiceValidator.used(dice, "6 3"));

        assertArrayEquals(new boolean[]{true, false, false}, DiceValidator.used(dice, "1 4"));
        assertArrayEquals(new boolean[]{false, true, false}, DiceValidator.used(dice, "2 5"));
        assertArrayEquals(new boolean[]{false, false, true}, DiceValidator.used(dice, "3 6"));

        assertArrayEquals(new boolean[]{true, false, false}, DiceValidator.used(dice, "4 1 4"));
        assertArrayEquals(new boolean[]{false, true, false}, DiceValidator.used(dice, "5 2 5"));
        assertArrayEquals(new boolean[]{false, false, true}, DiceValidator.used(dice, "6 3 6"));

        assertArrayEquals(new boolean[]{true, false, false}, DiceValidator.used(dice, "10"));
        assertArrayEquals(new boolean[]{true, false, false}, DiceValidator.used(dice, "100"));
        assertArrayEquals(new boolean[]{true, false, false}, DiceValidator.used(dice, "1 10 100"));
        assertArrayEquals(new boolean[]{true, false, false}, DiceValidator.used(dice, "4 5 100"));
    }

    private void assertArrayEquals(final boolean[] expected, final boolean[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
