package com.mgmf.monglaivemonfoie.event.action;

import com.mgmf.monglaivemonfoie.event.ActionEvent;
import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.event.drink.TakeDrinkEvent;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Event for the battle.
 *
 * @author Mathieu Aimé
 */

public abstract class BattleEvent extends ActionEvent {

    protected BattleEvent(Player... player) {
        super(1, player);
    }

    @Override
    public String play() {
        if (players.size() > 1) {
            StringBuilder builder = new StringBuilder();
            Player player1 = players.get(0);
            Player player2 = players.get(1);
            builder.append("Bataille de ")
                    .append(getRole())
                    .append(" entre ")
                    .append(player1.getName())
                    .append(" et ")
                    .append(player2.getName())
                    .append('\n');

            Dice player1Die = new Dice();
            Dice player2Die = new Dice();
            int nbGorgees = nb;

            do {
                DiceUtil.roll(player1Die, player2Die);
                builder.append(player1.getName()).append(" fait ").append(player1Die.getValue()).append("\n");
                builder.append(player2.getName()).append(" fait ").append(player2Die.getValue()).append("\n");
                if (player1Die.getValue() == player2Die.getValue()) {
                    nbGorgees <<= 1;
                    builder.append("Egalité, on recommence pour le double").append("\n");
                }
            } while (player1Die.getValue() == player2Die.getValue());

            Player winner = player1Die.getValue() > player2Die.getValue() ? player1 : player2;
            Player looser = player1Die.getValue() < player2Die.getValue() ? player1 : player2;

            builder.append(winner.getName()).append(" a gagné !").append('\n');

            List<Event> events = new ArrayList<>();
            events.add(new TakeDrinkEvent(nbGorgees * Math.abs(player1Die.getValue() - player2Die.getValue()), looser));
            for (Event e : events) {
                builder.append(e.play()).append('\n');
            }

            actionToWinner(winner, events);

            return builder.toString();
        } else {
            throw new IllegalArgumentException("A god battle must be between 2 players");
        }
    }

    protected abstract void actionToWinner(Player winner, List<Event> events);

    protected abstract String getRole();
}
