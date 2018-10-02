package org.jboss.schlawiner.engine.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumbersTest {
    Numbers numbers;

    @BeforeEach
    void setUp() {
        numbers = new Numbers();
    }

    @Test
    void newInstance() {
        assertEquals(-1, numbers.index());
        assertEquals(-1, numbers.current());
        assertFalse(numbers.hasNext());
        assertTrue(numbers.isEmpty());
        assertEquals(0, numbers.size());
    }

    @Test
    void next() {
        numbers.reset(10);
        assertEquals(-1, numbers.index());
        assertEquals(-1, numbers.current());
        assertFalse(numbers.isEmpty());
        assertEquals(10, numbers.size());
        assertTrue(0 != numbers.next());
        assertTrue(0 != numbers.current());
        assertEquals(0, numbers.index());
    }

    @Test
    void hasNext() {
        numbers.reset(1);
        assertFalse(numbers.isEmpty());
        assertEquals(1, numbers.size());
        assertTrue(numbers.hasNext());
        assertTrue(0 != numbers.next());
        assertEquals(0, numbers.index());
        assertFalse(numbers.hasNext());
    }
}
