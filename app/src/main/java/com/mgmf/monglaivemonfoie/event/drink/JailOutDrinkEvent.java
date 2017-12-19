package com.mgmf.monglaivemonfoie.event.drink;

import com.mgmf.monglaivemonfoie.event.DrinkEvent;
import com.mgmf.monglaivemonfoie.model.Player;

/**
 * Event for the jail out drink.
 *
 * @author Mathieu Aimé
 */

public class JailOutDrinkEvent extends DrinkEvent {

    public JailOutDrinkEvent(int nb, Player... player) {
        super(nb, player);
    }

    @Override
    public String getAction() {
        return "sort de prison ! Pour féter ça, il boit";
    }
}
