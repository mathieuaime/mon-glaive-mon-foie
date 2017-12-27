package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.Arrays;

/**
 * Event for the drinks.
 *
 * @author Mathieu Aimé
 */

public abstract class DrinkEvent extends Event {

    protected DrinkEvent(int nb, Player... player) {
        this.nb = nb;
        this.players = Arrays.asList(player);
    }

    @Override
    public String play() {
        StringBuilder builder = new StringBuilder();
        for (Player p : players) {
            if (!builder.toString().equals("")) {
                builder.append('\n');
            }

            builder.append(p.getName())
                    .append(" ")
                    .append(getAction())
                    .append(" ")
                    .append(DiceUtil.displayGorgees(nb));
        }
        return builder.toString();
    }

    public abstract String getAction();
}
