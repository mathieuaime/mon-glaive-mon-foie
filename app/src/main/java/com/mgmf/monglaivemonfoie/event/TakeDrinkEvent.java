package com.mgmf.monglaivemonfoie.event;

/**
 * Event for the drink taken.
 *
 * @author Mathieu Aimé
 */

public class TakeDrinkEvent extends DrinkEvent {
    public TakeDrinkEvent(int nb) {
        this.nb = nb;
    }

    @Override
    public String getAction() {
        return "boit";
    }
}
