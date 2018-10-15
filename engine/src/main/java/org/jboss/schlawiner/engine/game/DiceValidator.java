package org.jboss.schlawiner.engine.game;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import static com.google.common.base.Strings.emptyToNull;

public final class DiceValidator {

    private final static int[] MULTIPLIERS = new int[]{1, 10, 100};

    public static void validate(Dice dice, String term) throws ArithmeticException {
        int termNumbers[] = extractTermNumbers(term, true);
        if (termNumbers.length < dice.numbers.length) {
            throw new ArithmeticException("The term contains not all dice numbers.");
        } else if (termNumbers.length > dice.numbers.length) {
            throw new ArithmeticException("The term contains more numbers than diced.");
        } else {
            boolean[] used = internalUsed(dice, termNumbers);
            for (int i = 0; i < used.length; i++) {
                if (!used[i]) {
                    throw new ArithmeticException("You have not used all the dice numbers.");
                }
            }
        }
    }

    public static boolean[] used(Dice dice, String term) {
        int termNumbers[] = extractTermNumbers(term, false);
        return internalUsed(dice, termNumbers);
    }

    private static boolean[] internalUsed(Dice dice, int[] termNumbers) {
        boolean[] used = new boolean[dice.numbers.length];

        number:
        for (int i = 0; i < termNumbers.length; i++) {
            for (int j = 0; j < dice.numbers.length; j++) {
                if (!used[j]) {
                    for (int k = 0; k < MULTIPLIERS.length; k++) {
                        used[j] = termNumbers[i] == dice.numbers[j] * MULTIPLIERS[k];
                        if (used[j]) {
                            continue number;
                        }
                    }
                }
            }
        }
        return used;
    }

    private static int[] extractTermNumbers(String term, boolean picky) {
        if (emptyToNull(term) == null || term.trim().length() == 0) {
            if (picky) {
                throw new ArithmeticException("Empty term");
            } else {
                return new int[0];
            }
        }

        Iterable<String> numbers = Splitter.on(CharMatcher.inRange('0', '9').negate())
            .trimResults()
            .omitEmptyStrings()
            .split(term);
        int index = 0;
        int[] result = new int[Iterables.size(numbers)];
        for (String number : numbers) {
            try {
                result[index++] = Integer.valueOf(number);
            } catch (NumberFormatException e) {
                if (picky) {
                    throw new ArithmeticException(number + " is invalid");
                }
            }
        }
        return result;
    }

    private DiceValidator() {
    }
}
