package com.mgmf.monglaivemonfoie.model;

import com.mgmf.monglaivemonfoie.App;
import com.mgmf.monglaivemonfoie.R;

/**
 * Enumemrate all the roles.
 *
 * @author Mathieu Aimé
 */

public enum Role {
    HEROS(R.string.roleHero),
    DIEU(R.string.roleGod),
    ORACLE(R.string.roleOracle),
    ECUYER(R.string.roleSquire),
    PRISONNIER(R.string.rolePrisoner),
    CATIN(R.string.roleWhore),
    AUBERGISTE(R.string.roleInnkeeper),
    PRINCESSE(R.string.rolePrincess),
    DRAGON(R.string.roleDragon),
    CLOCHARD(R.string.roleBum),
    DEVIN(R.string.roleSoothsayer),
    APPRENTI(R.string.roleApprentice),
    GOURGANDINE(R.string.roleHussy),
    IMPERATRICE(R.string.roleEmpress),
    DEMON(R.string.roleDevil),
    ATTACK(R.string.roleAttack),
    DRINK(R.string.roleDrink),
    ALL_DRINK(R.string.roleAllDrink);

    private final String displayName;

    Role(final int role) {
        this.displayName = App.getAppContext().getString(role);
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
