package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Player;

/**
 * Created by Mathieu on 07/12/2017.
 */

public abstract class Event {
    protected int nb;
    public abstract void play(Player... playerSet);
}
