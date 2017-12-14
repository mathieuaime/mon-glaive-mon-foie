package com.mgmf.monglaivemonfoie.util;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.model.Dice;

import java.util.Arrays;

/**
 * Util class for the dices.
 * @author Mathieu Aimé
 */

@SuppressLint("NewApi")
public class DiceUtil {
    public static final int FACES = 6;

    public static void roll(Dice... dices) {
        Arrays.stream(dices).forEach(d -> d.roll(FACES));
    }

    public static void display(Dice... dices) {
        System.out.println("Dés : " + Arrays.toString(dices));
    }

    public static int numberOf(int value, Dice... dices) {
        return (int) Arrays.stream(dices).filter(x -> x.getValue() == value).count();
    }
}
