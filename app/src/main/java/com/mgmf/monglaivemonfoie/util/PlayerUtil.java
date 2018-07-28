package com.mgmf.monglaivemonfoie.util;

import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;

import java.util.Collection;

/**
 * Utility class for the players.
 *
 * @author Mathieu Aimé
 */

public class PlayerUtil {

    private PlayerUtil() {}

    public static boolean existRole(Role role, Collection<Player> players) {
        for (Player p : players) {
            if (p.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    public static Player getPlayerByRole(Role role, Collection<Player> players) {
        return getPlayerByRole(role, players.toArray(new Player[players.size()]));
    }

    private static Player getPlayerByRole(Role role, Player... players) {
        for (Player p : players) {
            if (p.hasRole(role)) {
                return p;
            }
        }
        return null;
    }
}
