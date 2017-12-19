package com.mgmf.monglaivemonfoie.event;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    @SuppressLint("NewApi")
    @Override
    public String play() {
        return players.stream().map(p -> p.getName() + " " + getAction() + " " + DiceUtil.displayGorgees(nb)).collect(Collectors.joining("\n"));
    }

    public abstract String getAction();
}
