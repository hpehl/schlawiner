package org.jboss.schlawiner.engine.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceGeneratorTest {
    private DiceGenerator diceGenerator;

    @BeforeEach
    void setUp() {
        diceGenerator = new DiceGenerator();
    }

    @Test
    void dice() {
        for (int i = 0; i < 100; i++) {
            int[] numbers = diceGenerator.numbers();
            assertEquals(3, numbers.length);
            for (int pip : numbers) {
                assertTrue(pip > 0);
                assertTrue(pip < 7);
            }
        }
    }
}
