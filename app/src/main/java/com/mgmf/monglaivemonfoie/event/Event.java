package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Player;

/**
 * Abstract Event.
 *
 * @author Mathieu Aimé
 */

public abstract class Event {
    protected int nb;

    public abstract void play(Player... playerSet);
}
