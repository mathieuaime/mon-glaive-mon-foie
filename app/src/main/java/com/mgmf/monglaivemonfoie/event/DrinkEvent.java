package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Player;

import java.util.Set;

/**
 * Created by Mathieu on 07/12/2017.
 */

public abstract class DrinkEvent extends Event {
    protected int nb;
    protected Set<Player> players;
}
