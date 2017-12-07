package com.mgmf.monglaivemonfoie.util;

import com.mgmf.monglaivemonfoie.model.Dice;

/**
 * Created by Mathieu on 06/12/2017.
 */

public class DiceUtil {
    public static void roll(Dice... dices) {
        for (Dice dice : dices) {
            dice.roll();
        }
    }
}
