package org.jboss.schlawiner.engine.game;

import java.util.Iterator;
import java.util.Random;

import com.google.common.collect.Iterators;

public class Numbers implements Iterable<Integer> {

    private final int count;
    private int index;
    private int current;
    private Integer[] numbers;

    public Numbers(int count) {
        this.count = count;
        this.index = -1;
        this.current = -1;
        this.numbers = new Integer[count];
        Random random = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 1 + random.nextInt(100);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return Iterators.forArray(numbers);
    }

    public int next() {
        index++;
        if (index < numbers.length) {
            current = numbers[index];
            return current;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public boolean hasNext() {
        return index + 1 < numbers.length;
    }

    public int index() {
        return index;
    }

    public int size() {
        return numbers.length;
    }

    public int current() {
        return current;
    }

    public int get(int index) {
        if (index > -1 && index < numbers.length) {
            return numbers[index];
        }
        return -1;
    }

    public boolean isEmpty() {
        return numbers.length == 0;
    }
}
