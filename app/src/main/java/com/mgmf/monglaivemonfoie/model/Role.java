package com.mgmf.monglaivemonfoie.model;

import android.content.res.Resources;

import com.mgmf.monglaivemonfoie.R;

/**
 * Enumemrate all the roles.
 *
 * @author Mathieu Aimé
 */

public enum Role {
    Heros(R.string.roleHero),
    Dieu(R.string.roleGod),
    Oracle(R.string.roleOracle),
    Ecuyer(R.string.roleSquire),
    Prisonnier(R.string.rolePrisoner),
    Catin(R.string.roleWhore),
    Aubergiste(R.string.roleInnkeeper),
    Princesse(R.string.rolePrincess),
    Dragon(R.string.roleDragon),
    Clochard(R.string.roleBum),
    Devin(R.string.roleSoothsayer),
    Apprenti(R.string.roleApprentice),
    Gourgandine(R.string.roleHussy),
    Imperatrice(R.string.roleEmpress),
    Demon(R.string.roleDevil),
    Attaque(R.string.roleAttack),
    Drink(R.string.roleDrink),
    AllDrink(R.string.roleAllDrink);

    private final String displayName;

    Role(final int role) {
        this.displayName = Constants.resources.getString(role);
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    private static class Constants {
        private static final Resources resources = Resources.getSystem();
    }
}
