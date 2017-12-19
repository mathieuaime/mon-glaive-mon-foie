package com.mgmf.monglaivemonfoie.event.drink;

import com.mgmf.monglaivemonfoie.event.DrinkEvent;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

/**
 * Event for the general drink.
 *
 * @author Mathieu Aimé
 */

public class GeneralDrinkEvent extends DrinkEvent {
    private int dice;

    public GeneralDrinkEvent(int dice, int nb, Player... player) {
        super(nb, player);
        this.dice = dice;
    }

    @Override
    public String getAction() {
        return "Tout le monde boit";
    }

    @Override
    public String play() {
        return dice + " ! " + getAction() + " " + DiceUtil.displayGorgees(nb);
    }
}
