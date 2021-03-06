package com.mgmf.monglaivemonfoie.event.action;

import android.content.Context;

import com.mgmf.monglaivemonfoie.App;
import com.mgmf.monglaivemonfoie.R;
import com.mgmf.monglaivemonfoie.decider.PlayerDecider;
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
    private List<Event> events;

    BattleEvent(List<Event> events, Player... player) {
        super(1, player);
        this.events = events;
    }

    @Override
    public String play() {
        if (players.size() > 1) {
            StringBuilder builder = new StringBuilder();
            Player player1 = players.get(0);
            Player player2 = players.get(1);
            Player apprenti = players.get(2);

            Context context = App.getAppContext();

            builder.append(String.format(context.getString(R.string.battle), getBattle(), player1.getName(), player2.getName()))
                    .append(NEW_LINE);

            Dice player1Die = new Dice();
            Dice player2Die = new Dice();

            do {
                DiceUtil.roll(player1Die, player2Die);
                builder.append(String.format(context.getString(R.string.actionBattle), player1.getName(), player1Die.getValue()))
                        .append(NEW_LINE)
                        .append(String.format(context.getString(R.string.actionBattle), player2.getName(), player2Die.getValue()))
                        .append(NEW_LINE);

                if (player1Die.getValue() == player2Die.getValue()) {
                    nb <<= 1;
                    builder.append(context.getString(R.string.battleEquality))
                            .append(NEW_LINE);
                }

            } while (player1Die.getValue() == player2Die.getValue());

            Player winner = player1Die.getValue() > player2Die.getValue() ? player1 : player2;
            Player looser = player1Die.getValue() < player2Die.getValue() ? player1 : player2;

            builder.append(String.format(context.getString(R.string.battleWin), winner.getName()))
                    .append(NEW_LINE);

            List<Event> events = new ArrayList<>();
            int drink = nb * Math.abs(player1Die.getValue() - player2Die.getValue());
            events.add(new TakeDrinkEvent(drink, looser));
            PlayerDecider.takeDrinkIfExist(events, apprenti, drink);
            for (Event e : events) {
                builder.append(e.play()).append(NEW_LINE);
            }

            actionToWinner(winner, events);

            return builder.toString();
        } else {
            throw new IllegalArgumentException("A battle must be between 2 players");
        }
    }

    protected abstract void actionToWinner(Player winner, List<Event> events);

    protected abstract String getBattle();
}
