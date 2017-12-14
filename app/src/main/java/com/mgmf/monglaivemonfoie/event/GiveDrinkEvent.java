package com.mgmf.monglaivemonfoie.event;

/**
 * Created by Mathieu on 07/12/2017.
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
