package org.jboss.schlawiner.engine.game;

import java.util.Random;

public class DiceGenerator {

    public int[] numbers() {
        int[] numbers = new int[3];
        Random random = new Random();
        numbers[0] = 1 + random.nextInt(6);
        numbers[1] = 1 + random.nextInt(6);
        numbers[2] = 1 + random.nextInt(6);
        return numbers;
    }
}
