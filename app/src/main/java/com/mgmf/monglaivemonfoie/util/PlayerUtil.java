package com.mgmf.monglaivemonfoie.util;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Utility class for the players.
 *
 * @author Mathieu Aimé
 */

public class PlayerUtil {
    @SuppressLint("NewApi")
    public static boolean isRole(Role role, Collection<Player> players) {
        return players.stream().anyMatch(p -> p.hasRole(role));
    }

    public static Optional<Player> getPlayerByRole(Role role, Collection<Player> players) {
        return getPlayerByRole(role, players.toArray(new Player[players.size()]));
    }

    @SuppressLint("NewApi")
    public static Optional<Player> getPlayerByRole(Role role, Player... players) {
        return Arrays.stream(players).filter(p -> p.hasRole(role)).findFirst();
    }
}
