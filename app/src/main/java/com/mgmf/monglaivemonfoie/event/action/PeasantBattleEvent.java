package com.mgmf.monglaivemonfoie.event.action;

import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.model.Player;

import java.util.List;

/**
 * Event for the peasant battle.
 *
 * @author Mathieu Aimé
 */

public class PeasantBattleEvent extends BattleEvent {

    public PeasantBattleEvent(int nb, Player... player) {
        super(nb, player);
    }

    @Override
    protected void actionToWinner(Player winner, List<Event> events) {
    }

    @Override
    protected String getRole() {
        return "peasant";
    }

}