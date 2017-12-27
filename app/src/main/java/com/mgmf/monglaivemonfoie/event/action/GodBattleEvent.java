package com.mgmf.monglaivemonfoie.event.action;

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
 * Event for the god battle.
 *
 * @author Mathieu Aimé
 */

public class GodBattleEvent extends ActionEvent {

    public GodBattleEvent(int nb, Player... player) {
        super(nb, player);
    }

    @Override
    public String play() {
        if (players.size() > 1) {
            StringBuilder builder = new StringBuilder();
            Player oldGod = players.get(0);
            Player player = players.get(1);
            builder.append("Bataille de dieux entre ")
                    .append(oldGod.getName())
                    .append(" et ")
                    .append(player.getName())
                    .append('\n');

            Dice oldGodDice = new Dice();
            Dice playerDice = new Dice();
            int nbGorgees = nb;

            do {
                DiceUtil.roll(oldGodDice, playerDice);
                builder.append(oldGod.getName()).append(" fait ").append(oldGodDice.getValue()).append("\n");
                builder.append(player.getName()).append(" fait ").append(playerDice.getValue()).append("\n");
                if (oldGodDice.getValue() == playerDice.getValue()) {
                    nbGorgees *= 2;
                    builder.append("Egalité, on recommence pour ").append(DiceUtil.displayGorgees(nbGorgees)).append("\n");
                }
            } while (oldGodDice.getValue() == playerDice.getValue());

            Player winner = oldGodDice.getValue() > playerDice.getValue() ? oldGod : player;
            Player looser = oldGodDice.getValue() < playerDice.getValue() ? oldGod : player;

            builder.append(winner.getName()).append(" a gagné !").append('\n');

            List<Event> events = new ArrayList<>();
            events.add(new TakeDrinkEvent(nbGorgees, looser));
            PlayerDecider.becomeGod(events, winner, players);
            for (Event e : events) {
                builder.append(e.play())
                        .append('\n');
            }

            return builder.toString();
        } else {
            throw new IllegalArgumentException("A god battle must be between 2 players");
        }
    }
}
