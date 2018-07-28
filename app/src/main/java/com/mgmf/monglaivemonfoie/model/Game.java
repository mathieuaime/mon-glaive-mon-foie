package com.mgmf.monglaivemonfoie.model;

import com.mgmf.monglaivemonfoie.decider.PlayerDecider;
import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.util.DiceUtil;
import com.mgmf.monglaivemonfoie.util.PlayerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for a game.
 *
 * @author Mathieu Aimé
 */

public class Game {
    private final int nbPlayers;

    private final Dice dice1 = new Dice();
    private final Dice dice2 = new Dice();
    private final Dice specialDice = new Dice(true);
    private final List<Player> players = new ArrayList<>();

    private int actualPlayer = 0;

    public Game(List<String> players) {
        this.nbPlayers = players.size();
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
        actualPlayer = (actualPlayer + 1) % nbPlayers;
        return PlayerDecider.play(player, getDices(), players);
    }

    public Dices getDices() {
        return new Dices(dice1, dice2, specialDice);
    }

    public Player getPreviousPlayer() {
        int index = (actualPlayer - 1) % nbPlayers;
        if (index < 0) index += nbPlayers;
        return players.get(index);
    }

    public Player getActualPlayer() {
        return players.get(actualPlayer);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getApprentice() {
        return PlayerUtil.getPlayerByRole(Role.APPRENTI, players);
    }
}
