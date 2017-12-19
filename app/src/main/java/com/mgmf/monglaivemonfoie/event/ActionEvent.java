package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Player;

import java.util.Arrays;

/**
 * Event for the actions.
 *
 * @author Mathieu Aimé
 */

public abstract class ActionEvent extends Event {
    protected ActionEvent(int nb, Player... player) {
        this.nb = nb;
        this.players = Arrays.asList(player);
    }
}
