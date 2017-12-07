package com.mgmf.monglaivemonfoie.util;

import android.support.annotation.NonNull;

import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Role;
import com.mgmf.monglaivemonfoie.validator.DiceValidator;

/**
 * Created by Mathieu on 06/12/2017.
 */

public class RoleUtil {
    public static Role play(Dice dice1, Dice dice2, Dice specialDice) {
        DiceValidator.validate(dice1, dice2, specialDice);

        int d1 = Math.max(dice1.getValue(), dice2.getValue());
        int d2 = Math.min(dice1.getValue(), dice2.getValue());

        if (d1 == d2) {
            if (d1 == specialDice.getValue()) {
                return getSuperRole(d1 - 1);
            } else {
                return d1 > 3 ? Role.Dieu : Role.Heros;
            }
        } else if (d1 + d2 == 7) {
            return Role.Attaque;
        } else if (d1 + d2 == 6) {
            return Role.AllDrink;
        } else {
            return getRole(d1, d2);
        }
    }

    @NonNull
    private static Role getRole(int dice1, int dice2) {
        switch (dice1) {
            case 2:
                return Role.Oracle;
            case 3:
                return dice2 == 1 ? Role.Ecuyer : Role.Prisonnier;
            case 4:
                return Role.Catin;
            case 5:
                return dice2 == 3 ? Role.Aubergiste : Role.Princesse;
            default:
                return dice2 == 5 ? Role.Dragon : Role.Drink;
        }
    }

    private static Role getSuperRole(int index) {
        return new Role[]{Role.Clochard, Role.Devin, Role.Apprenti, Role.Gourgandine, Role.Imperatrice, Role.Demon}[index];
    }

    public static Role getRoleFromSuperRole(Role role) {
        switch (role) {
            case Demon:
                return Role.Dieu;
            case Imperatrice:
                return Role.Princesse;
            case Gourgandine:
                return Role.Catin;
            case Apprenti:
                return Role.Ecuyer;
            case Devin:
                return Role.Oracle;
            case Clochard:
                return Role.Heros;
            default:
                return role;
        }
    }

    public static Role getSuperRoleFromRole(Role role) {
        switch (role) {
            case Dieu:
                return Role.Demon;
            case Princesse:
                return Role.Imperatrice;
            case Catin:
                return Role.Gourgandine;
            case Ecuyer:
                return Role.Apprenti;
            case Oracle:
                return Role.Devin;
            case Heros:
                return Role.Clochard;
            default:
                return role;
        }
    }
}
