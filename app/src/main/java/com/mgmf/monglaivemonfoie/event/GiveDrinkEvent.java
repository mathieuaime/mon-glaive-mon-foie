package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Player;

import java.util.HashSet;

/**
 * Created by Mathieu on 07/12/2017.
 */

public class GiveDrinkEvent extends DrinkEvent {
    public GiveDrinkEvent(int nb, Player player) {
        this.nb = nb;
        this.players = new HashSet<>();
        this.players.add(player);
    }
}
