package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Player;

/**
 * Created by Mathieu on 07/12/2017.
 */

public class GodBattleEvent extends Event {
    private Player oldGod;
    private Player player;

    public GodBattleEvent(Player oldGod, Player player) {
        super();
        this.oldGod = oldGod;
        this.player = player;
    }

    public Player getOldGod() {
        return oldGod;
    }

    public Player getPlayer() {
        return player;
    }
}
