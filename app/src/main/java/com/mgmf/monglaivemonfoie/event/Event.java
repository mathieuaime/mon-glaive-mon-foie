package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Player;

import java.util.List;

/**
 * Abstract Event.
 *
 * @author Mathieu Aimé
 */

public abstract class Event {
    protected int nb;
    protected List<Player> players;

    public abstract String play();
}
