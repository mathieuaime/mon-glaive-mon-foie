package com.mgmf.monglaivemonfoie.event.action;

import com.mgmf.monglaivemonfoie.decider.PlayerDecider;
import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.model.Player;

import java.util.List;

/**
 * Event for the god battle.
 *
 * @author Mathieu Aimé
 */

public class GodBattleEvent extends BattleEvent {

    public GodBattleEvent(Player... player) {
        super(player);
    }

    @Override
    protected void actionToWinner(Player winner, List<Event> events) {
        PlayerDecider.becomeGod(events, winner, players);
    }

    @Override
    protected String getRole() {
        return "dieu";
    }

}
