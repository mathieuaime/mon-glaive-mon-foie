package com.mgmf.monglaivemonfoie.event.action;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.decider.PlayerDecider;
import com.mgmf.monglaivemonfoie.event.ActionEvent;
import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Event for the god battle.
 *
 * @author Mathieu Aimé
 */

public class GodBattleEvent extends ActionEvent {

    public GodBattleEvent(int nb, Player... player) {
        super(nb, player);
    }

    private static Player getWinner(Player oldGod, Player player) {
        Dice oldGodDice = new Dice();
        Dice playerDice = new Dice();

        do {
            DiceUtil.roll(oldGodDice, playerDice);
        } while (oldGodDice.getValue() == playerDice.getValue());

        return oldGodDice.getValue() > playerDice.getValue() ? oldGod : player;
    }

    @SuppressLint("NewApi")
    @Override
    public String play() {
        if (players.size() > 1) {
            StringBuilder builder = new StringBuilder();
            Player oldGod = players.get(0);
            Player player = players.get(1);
            builder.append("Bataille de dieux entre ").append(oldGod.getName()).append(" et ").append(player.getName()).append('\n');
            Player winner = getWinner(oldGod, player);

            builder.append(winner.getName()).append(" a gagné !").append('\n');

            List<Event> events = new ArrayList<>();
            PlayerDecider.becomeGod(events, winner, players);
            events.stream().map(e -> e.play() + '\n').forEach(builder::append);

            return builder.toString();
        } else {
            throw new IllegalArgumentException("A god battle must be between 2 players");
        }
    }
}
