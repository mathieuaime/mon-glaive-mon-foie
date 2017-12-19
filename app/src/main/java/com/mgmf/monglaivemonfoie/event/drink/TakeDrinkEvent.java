package com.mgmf.monglaivemonfoie.event.drink;

import com.mgmf.monglaivemonfoie.event.DrinkEvent;
import com.mgmf.monglaivemonfoie.model.Player;

/**
 * Event for the drink taken.
 *
 * @author Mathieu Aimé
 */

public class TakeDrinkEvent extends DrinkEvent {
    public TakeDrinkEvent(int nb, Player... player) {
        super(nb, player);
    }

    @Override
    public String getAction() {
        return "boit";
    }
}
