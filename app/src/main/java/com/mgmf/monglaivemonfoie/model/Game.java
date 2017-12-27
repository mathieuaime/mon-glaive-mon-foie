package com.mgmf.monglaivemonfoie.model;

import com.mgmf.monglaivemonfoie.decider.PlayerDecider;
import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for a game.
 *
 * @author Mathieu Aimé
 */

public class Game {
    private final int NB_PLAYERS;

    private final Dice dice1 = new Dice();
    private final Dice dice2 = new Dice();
    private final Dice specialDice = new Dice(true);
    private final List<Player> players = new ArrayList<>();

    private int actualPlayer = 0;

    public Game(int NB_PLAYERS) {
        this.NB_PLAYERS = NB_PLAYERS;

        for (int i = 1; i <= NB_PLAYERS; i++) {
            players.add(new Player("Player" + i));
        }
    }

    public void roll() {
        DiceUtil.roll(dice1, dice2, specialDice);
    }

    public List<Event> play() {
        Player player = players.get(actualPlayer);
        actualPlayer = (actualPlayer + 1) % NB_PLAYERS;
        return PlayerDecider.play(player, dice1, dice2, specialDice, players);
    }

    public Dice[] getDices() {
        return new Dice[]{dice1, dice2, specialDice};
    }

    public Player getActualPlayer() {
        return players.get(actualPlayer);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
