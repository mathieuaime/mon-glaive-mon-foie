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

    public Game(List<String> players) {
        this.NB_PLAYERS = players.size();
        for (String player : players) {
            this.players.add(new Player(player));
        }
    }

    public boolean roll() {
        int d1 = dice1.getValue();
        int d2 = dice2.getValue();

        DiceUtil.roll(dice1, dice2, specialDice);

        return (d1 == dice1.getValue() && d2 == dice2.getValue()) || (d1 == dice2.getValue() && d2 == dice1.getValue());
    }

    public List<Event> play() {
        Player player = players.get(actualPlayer);
        actualPlayer = (actualPlayer + 1) % NB_PLAYERS;
        return PlayerDecider.play(player, dice1, dice2, specialDice, players);
    }

    public Dice[] getDices() {
        return new Dice[]{dice1, dice2, specialDice};
    }

    public Player getPreviousPlayer() {
        int index = (actualPlayer - 1) % NB_PLAYERS;
        if (index < 0) index += NB_PLAYERS;
        return players.get(index);
    }

    public Player getActualPlayer() {
        return players.get(actualPlayer);
    }

    public List<Player> getPlayers() {
        return players;
    }

}
