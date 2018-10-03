package org.jboss.schlawiner.engine.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumbersTest {

    private Numbers numbers;

    @BeforeEach
    void setUp() {
        numbers = new Numbers(10);
    }

    @Test
    void newInstance() {
        assertEquals(-1, numbers.current());
        assertEquals(-1, numbers.index());
        assertEquals(10, numbers.size());
        assertTrue(0 != numbers.next());
        assertTrue(numbers.hasNext());
        assertFalse(numbers.isEmpty());

    }

    @Test
    void next() {
        numbers.next();

        assertEquals(0, numbers.index());
        assertEquals(10, numbers.size());
        assertTrue(0 != numbers.current());
        assertTrue(0 != numbers.next());
        assertTrue(numbers.hasNext());
        assertFalse(numbers.isEmpty());
    }
}
