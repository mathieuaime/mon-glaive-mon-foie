package com.mgmf.monglaivemonfoie.event.drink;

import com.mgmf.monglaivemonfoie.event.DrinkEvent;
import com.mgmf.monglaivemonfoie.model.Player;

/**
 * Event for the jail in drink.
 *
 * @author Mathieu Aimé
 */

public class JailInDrinkEvent extends DrinkEvent {
    public JailInDrinkEvent(int nb, Player... player) {
        super(nb, player);
    }

    @Override
    public String getAction() {
        return "rentre en prison ! Pour féter ça, il boit";
    }
}
