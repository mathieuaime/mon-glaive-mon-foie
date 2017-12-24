package com.mgmf.monglaivemonfoie.util;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.model.Dice;

import java.util.StringJoiner;

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

    @SuppressLint("NewApi")
    public static String displayDices(Dice[] dices) {
        StringJoiner joiner = new StringJoiner(" ");
        for (Dice d : dices) {
            joiner.add(String.valueOf(d.getValue()));
        }
        return joiner.toString();
    }
}
