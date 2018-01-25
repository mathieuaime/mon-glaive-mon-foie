package com.mgmf.monglaivemonfoie.event.action;

import com.mgmf.monglaivemonfoie.App;
import com.mgmf.monglaivemonfoie.R;
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
    public GodBattleEvent(List<Event> events, Player... player) {
        super(events, player);
    }

    @Override
    protected void actionToWinner(Player winner, List<Event> events) {
        PlayerDecider.becomeGod(events, winner, players);
    }

    @Override
    protected String getBattle() {
        return App.getAppContext().getString(R.string.godBattle);
    }

}
