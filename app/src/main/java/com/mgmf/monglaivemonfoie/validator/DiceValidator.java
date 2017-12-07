package com.mgmf.monglaivemonfoie.validator;

import com.mgmf.monglaivemonfoie.exception.BadDiceException;
import com.mgmf.monglaivemonfoie.model.Dice;

/**
 * Created by Mathieu on 06/12/2017.
 */

public class DiceValidator {

    public static void validate(Dice... dices) {
        for (Dice dice : dices) {
            validate(dice);
        }
    }

    private static void validate(Dice dice) {
        if (dice.getValue() <= 0 || dice.getValue() > Dice.FACES) {
            throw new BadDiceException();
        }
    }
}
