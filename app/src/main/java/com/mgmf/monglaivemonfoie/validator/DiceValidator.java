package com.mgmf.monglaivemonfoie.validator;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.exception.BadDiceException;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

/**
 * Validate a dice.
 *
 * @author Mathieu Aimé
 */

public class DiceValidator {

    public static void validate(Dice... dices) {
        for (Dice dice : dices) {
            validate(dice);
        }
    }

    private static void validate(Dice dice) {
        if (dice.getValue() <= 0 || dice.getValue() > DiceUtil.FACES) {
            throw new BadDiceException();
        }
    }
}
