package com.mgmf.monglaivemonfoie.util;

import com.mgmf.monglaivemonfoie.model.Dice;

/**
 * Utility class for the dices.
 *
 * @author Mathieu Aimé
 */

public class DiceUtil {
    public static final int FACES = 6;

    public static void roll(Dice... dices) {
        for (Dice d : dices) {
            d.roll(FACES);
        }
    }

    public static int numberOf(int value, Dice... dices) {
        int count = 0;
        for (Dice x : dices) {
            if (x.getValue() == value) {
                ++count;
            }
        }
        return count;
    }

    public static String displayGorgees(int value) {
        return value + " gorgée" + (value > 1 ? "s" : "");
    }
}
