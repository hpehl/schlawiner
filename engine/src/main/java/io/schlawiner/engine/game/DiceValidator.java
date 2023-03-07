/*
 *  Copyright 2023 Harald Pehl
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.schlawiner.engine.game;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DiceValidator {

    private static final int[] MULTIPLIERS = new int[] { 1, 10, 100 };
    private static final Pattern NUMBERS = Pattern.compile("\\d+");

    static void validate(final Dice dice, final String term) throws ArithmeticException {
        final int[] termNumbers = extractTermNumbers(term, true);
        if (termNumbers.length < dice.numbers().length) {
            throw new ArithmeticException("The term contains not all dice numbers.");
        } else if (termNumbers.length > dice.numbers().length) {
            throw new ArithmeticException("The term contains more numbers than diced.");
        } else {
            final boolean[] used = internalUsed(dice, termNumbers);
            for (final boolean b : used) {
                if (!b) {
                    throw new ArithmeticException("You have not used all the dice numbers.");
                }
            }
        }
    }

    public static boolean[] used(final Dice dice, final String term) {
        final int[] termNumbers = extractTermNumbers(term, false);
        return internalUsed(dice, termNumbers);
    }

    private static boolean[] internalUsed(final Dice dice, final int[] termNumbers) {
        final boolean[] used = new boolean[dice.numbers().length];

        number: for (final int termNumber : termNumbers) {
            for (int j = 0; j < dice.numbers().length; j++) {
                if (!used[j]) {
                    for (final int multiplier : MULTIPLIERS) {
                        used[j] = termNumber == dice.numbers()[j] * multiplier;
                        if (used[j]) {
                            continue number;
                        }
                    }
                }
            }
        }
        return used;
    }

    private static int[] extractTermNumbers(final String term, final boolean picky) {
        if (term == null || term.trim().length() == 0) {
            if (picky) {
                throw new ArithmeticException("Empty term");
            } else {
                return new int[0];
            }
        }

        final Matcher matcher = NUMBERS.matcher(term);
        final List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            final String number = matcher.group();
            try {
                numbers.add(Integer.valueOf(number));
            } catch (final NumberFormatException e) {
                if (picky) {
                    throw new ArithmeticException(number + " is invalid");
                }
            }
        }
        int index = 0;
        final int[] result = new int[numbers.size()];
        for (final Integer number : numbers) {
            result[index] = number;
            index++;
        }
        return result;
    }

    private DiceValidator() {
    }
}
