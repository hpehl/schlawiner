package org.jboss.schlawiner.engine.game;

import java.util.Random;

public class Dice {

    public int[] numbers;

    /** New dice with three random numbers */
    public Dice() {
        Random random = new Random();
        this.numbers = new int[3];
        this.numbers[0] = 1 + random.nextInt(6);
        this.numbers[1] = 1 + random.nextInt(6);
        this.numbers[2] = 1 + random.nextInt(6);
    }

    /** New dice with specified numbers */
    Dice(int number0, int number1, int number2) {
        this.numbers = new int[3];
        this.numbers[0] = number0;
        this.numbers[1] = number1;
        this.numbers[2] = number2;
    }

    @Override
    public String toString() {
        return numbers[0] + " " + numbers[1] + " " + numbers[2];
    }
}
