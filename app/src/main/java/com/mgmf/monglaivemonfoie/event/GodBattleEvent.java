package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.decider.PlayerDecider;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.Arrays;

/**
 * Event for the god battle.
 *
 * @author Mathieu Aimé
 */

public class GodBattleEvent extends Event {
    private static Player getWinner(Player oldGod, Player player) {
        Dice oldGodDice = new Dice();
        Dice playerDice = new Dice();

        do {
            DiceUtil.roll(oldGodDice, playerDice);
        } while (oldGodDice.getValue() == playerDice.getValue());

        return oldGodDice.getValue() > playerDice.getValue() ? oldGod : player;
    }

    @Override
    public void play(Player... playerSet) {
        if (playerSet.length > 1) {
            System.out.println("Bataille de dieux entre " + playerSet[0].getName() + " et " + playerSet[1].getName());
            Player winner = getWinner(playerSet[0], playerSet[1]);

            System.out.println(winner.getName() + " a gagné !");
            PlayerDecider.becomeGod(winner, Arrays.asList(playerSet));
        } else {
            throw new IllegalArgumentException("A god battle decideRole must be between 2 players");
        }
    }
}
