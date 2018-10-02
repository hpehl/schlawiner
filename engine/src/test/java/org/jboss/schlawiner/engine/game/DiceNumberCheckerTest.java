package org.jboss.schlawiner.engine.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiceNumberCheckerTest {

    private DiceNumberChecker diceNumberChecker;

    @BeforeEach
    void setUp() {
        diceNumberChecker = new DiceNumberChecker(new int[]{1, 2, 3});
    }

    @Test
    void checkNull() {
        assertThrows(ArithmeticException.class, () -> diceNumberChecker.check(null));
    }

    @Test
    public void checkEmpty() {
        assertThrows(ArithmeticException.class, () -> diceNumberChecker.check(""));
    }

    @Test
    void checkBlank() {
        assertThrows(ArithmeticException.class, () -> diceNumberChecker.check("       "));
    }

    @Test
    void oneNumber() {
        assertThrows(ArithmeticException.class, () -> diceNumberChecker.check("1"));
    }

    @Test
    void twoNumbers() {
        assertThrows(ArithmeticException.class, () -> diceNumberChecker.check("1 2"));
    }

    @Test
    void fourNumbers() {
        assertThrows(ArithmeticException.class, () -> diceNumberChecker.check("1 2 3 4"));
    }

    @Test
    void wrongNumbers() {
        assertThrows(ArithmeticException.class, () -> diceNumberChecker.check("1 2 4"));
    }

    @Test
    void check() {
        diceNumberChecker.check("1 2 3");
        diceNumberChecker.check("1 20 300");
    }

    @Test
    void used() {
        assertArrayEquals(new boolean[]{false, false, false}, diceNumberChecker.used(null));
        assertArrayEquals(new boolean[]{false, false, false}, diceNumberChecker.used(""));
        assertArrayEquals(new boolean[]{false, false, false}, diceNumberChecker.used("    "));

        assertArrayEquals(new boolean[]{true, false, false}, diceNumberChecker.used("1"));
        assertArrayEquals(new boolean[]{false, true, false}, diceNumberChecker.used("2"));
        assertArrayEquals(new boolean[]{false, false, true}, diceNumberChecker.used("3"));

        assertArrayEquals(new boolean[]{true, false, false}, diceNumberChecker.used("4 1"));
        assertArrayEquals(new boolean[]{false, true, false}, diceNumberChecker.used("5 2"));
        assertArrayEquals(new boolean[]{false, false, true}, diceNumberChecker.used("6 3"));

        assertArrayEquals(new boolean[]{true, false, false}, diceNumberChecker.used("1 4"));
        assertArrayEquals(new boolean[]{false, true, false}, diceNumberChecker.used("2 5"));
        assertArrayEquals(new boolean[]{false, false, true}, diceNumberChecker.used("3 6"));

        assertArrayEquals(new boolean[]{true, false, false}, diceNumberChecker.used("4 1 4"));
        assertArrayEquals(new boolean[]{false, true, false}, diceNumberChecker.used("5 2 5"));
        assertArrayEquals(new boolean[]{false, false, true}, diceNumberChecker.used("6 3 6"));

        assertArrayEquals(new boolean[]{true, false, false}, diceNumberChecker.used("10"));
        assertArrayEquals(new boolean[]{true, false, false}, diceNumberChecker.used("100"));
        assertArrayEquals(new boolean[]{true, false, false}, diceNumberChecker.used("1 10 100"));
        assertArrayEquals(new boolean[]{true, false, false}, diceNumberChecker.used("4 5 100"));
    }

    private void assertArrayEquals(final boolean[] expected, final boolean[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
