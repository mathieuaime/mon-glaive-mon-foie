package com.mgmf.monglaivemonfoie.event;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.model.Player;

import java.util.Arrays;

/**
 * Event for the drink.
 *
 * @author Mathieu Aimé
 */

public abstract class DrinkEvent extends Event {
    @SuppressLint("NewApi")
    public void play(Player... players) {
        Arrays.stream(players).forEach(p -> System.out.println(p.getName() + " " + getAction() + " " + nb + " gorgée" + (nb > 1 ? "s" : "")));
    }

    public abstract String getAction();
}
