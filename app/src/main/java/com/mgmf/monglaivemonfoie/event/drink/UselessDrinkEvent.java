package com.mgmf.monglaivemonfoie.event.drink;

import com.mgmf.monglaivemonfoie.event.DrinkEvent;
import com.mgmf.monglaivemonfoie.model.Player;

/**
 * Event for when you are fucking useless, so you drink.
 *
 * @author Mathieu Aimé
 */

public class UselessDrinkEvent extends DrinkEvent {
    public UselessDrinkEvent(int nb, Player... player) {
        super(nb, player);
    }

    @Override
    public String getAction() {
        return "est inutile, il boit";
    }
}
