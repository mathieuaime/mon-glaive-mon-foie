package com.mgmf.monglaivemonfoie.util;

import com.mgmf.monglaivemonfoie.model.Dice;

import java.util.Random;

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

    public static String displayDices(Dice[] dices) {
        StringBuilder builder = new StringBuilder();
        for (Dice d : dices) {
            builder.append(String.valueOf(d.getValue())).append(" ");
        }
        return builder.toString();
    }

    public static int random() {
        Random r = new Random();
        return r.nextInt(FACES) + 1;
    }
}
