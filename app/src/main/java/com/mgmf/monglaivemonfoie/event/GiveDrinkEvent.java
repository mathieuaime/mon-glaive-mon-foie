package com.mgmf.monglaivemonfoie.event;

/**
 * Event for the drink given.
 *
 * @author Mathieu Aimé
 */

public class GiveDrinkEvent extends DrinkEvent {
    public GiveDrinkEvent(int nb) {
        this.nb = nb;
    }

    @Override
    public String getAction() {
        return "donne";
    }
}
