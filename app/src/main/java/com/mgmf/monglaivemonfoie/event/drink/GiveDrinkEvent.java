package com.mgmf.monglaivemonfoie.event.drink;

import com.mgmf.monglaivemonfoie.event.DrinkEvent;
import com.mgmf.monglaivemonfoie.model.Player;

/**
 * Event for the drink given.
 *
 * @author Mathieu Aimé
 */

public class GiveDrinkEvent extends DrinkEvent {
    public GiveDrinkEvent(int nb, Player... player) {
        super(nb, player);
    }

    @Override
    public String getAction() {
        return "donne";
    }
}
