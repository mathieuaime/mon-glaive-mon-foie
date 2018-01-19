package com.mgmf.monglaivemonfoie.model;

/**
 * Enumemrate all the roles.
 *
 * @author Mathieu Aimé
 */

public enum Role {
    Heros("Héros"),
    Dieu("Dieu"),
    Oracle("Oracle"),
    Ecuyer("Ecuyer"),
    Prisonnier("Prisonnier"),
    Catin("Catin"),
    Aubergiste("Aubergiste"),
    Princesse("Princesse"),
    Dragon("Dragon"),
    Clochard("Clochard"),
    Devin("Devin"),
    Apprenti("Apprenti"),
    Gourgandine("Gourgandine"),
    Imperatrice("Impératrice"),
    Demon("Démon"),
    Attaque("Attaque de Dieu"),
    Drink("Tu donnes !"),
    AllDrink("Tout le monde boit !");

    private final String displayName;

    Role(final String display) {
        this.displayName = display;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
