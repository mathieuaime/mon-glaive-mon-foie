package com.mgmf.monglaivemonfoie.event;

/**
 * Event for when you are fucking useless, so you drink.
 *
 * @author Mathieu Aimé
 */

public class UselessDrinkEvent extends DrinkEvent {
    public UselessDrinkEvent(int nb) {
        this.nb = nb;
    }

    @Override
    public String getAction() {
        return "est inutile, il boit";
    }
}
