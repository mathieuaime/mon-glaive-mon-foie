package com.mgmf.monglaivemonfoie.validator;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.exception.BadDiceException;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.Arrays;

/**
 * Created by Mathieu on 06/12/2017.
 */

public class DiceValidator {

    @SuppressLint("NewApi")
    public static void validate(Dice... dices) {
        Arrays.stream(dices).forEach(DiceValidator::validate);
    }

    private static void validate(Dice dice) {
        if (dice.getValue() <= 0 || dice.getValue() > DiceUtil.FACES) {
            throw new BadDiceException();
        }
    }
}
