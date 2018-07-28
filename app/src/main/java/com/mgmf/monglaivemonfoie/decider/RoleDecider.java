package com.mgmf.monglaivemonfoie.decider;

import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Dices;
import com.mgmf.monglaivemonfoie.model.Role;
import com.mgmf.monglaivemonfoie.util.RoleUtil;
import com.mgmf.monglaivemonfoie.validator.DiceValidator;

/**
 * Contains the logic for the roles.
 *
 * @author Mathieu Aimé
 */

public class RoleDecider {
    private RoleDecider() {
    }

    private static Role decideRole(Dice dice1, Dice dice2, Dice specialDice) {
        DiceValidator.validate(dice1, dice2, specialDice);

        int d1 = Math.max(dice1.getValue(), dice2.getValue());
        int d2 = Math.min(dice1.getValue(), dice2.getValue());

        int index = d1 == d2 && d1 == specialDice.getValue() ? d1 * 100 + d1 * 10 + d1 : d1 * 10 + d2;

        return RoleUtil.getRoles().get(index);
    }

    public static Role decideRole(Dices dices) {
        if(dices.getDice1() == null || dices.getDice2() == null || dices.getSpecialDice() == null) {
            throw new IllegalArgumentException("Dices should not be null");
        }
        return decideRole(dices.getDice1(), dices.getDice2(), dices.getSpecialDice());
    }
}
