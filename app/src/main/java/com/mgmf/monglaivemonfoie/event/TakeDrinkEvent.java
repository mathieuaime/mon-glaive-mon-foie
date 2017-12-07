package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Player;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Mathieu on 07/12/2017.
 */

public class TakeDrinkEvent extends DrinkEvent {
    public TakeDrinkEvent(int nb, Player... players) {
        this.nb = nb;
        this.players = new HashSet<>(Arrays.asList(players));
    }
}
