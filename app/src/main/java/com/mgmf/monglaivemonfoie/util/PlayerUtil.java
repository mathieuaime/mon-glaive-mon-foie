package com.mgmf.monglaivemonfoie.util;

import com.mgmf.monglaivemonfoie.event.AttackEvent;
import com.mgmf.monglaivemonfoie.event.DrinkEvent;
import com.mgmf.monglaivemonfoie.event.GiveDrinkEvent;
import com.mgmf.monglaivemonfoie.event.GodBattleEvent;
import com.mgmf.monglaivemonfoie.event.TakeDrinkEvent;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;

import java.util.Collection;

/**
 * Created by Mathieu on 06/12/2017.
 */

public class PlayerUtil {
    public static void assignRoleToPlayer(Dice dice1, Dice dice2, Dice specialDice, Role role, Player player, Collection<Player> players) throws GodBattleEvent, DrinkEvent, AttackEvent {
        int d1 = Math.max(dice1.getValue(), dice2.getValue());
        int d2 = Math.min(dice1.getValue(), dice2.getValue());
        switch (role) {
            case Demon:
            case Imperatrice:
            case Gourgandine:
            case Apprenti:
            case Devin:
            case Clochard:
                assignSuperRoleToPlayer(role, player.getName(), players);
                return;
            case Dieu:
                if (d1 == 6) {
                    if (assignRoleToPlayer(role, player.getName(), players)) {
                        throw new TakeDrinkEvent(specialDice.getValue(), player);
                    }
                } else {
                    for (Player p : players) {
                        if (p.hasRole(role)) {
                            throw new GodBattleEvent(p, player);
                        }
                    }
                }
                return;
            case Catin:
                if (assignRoleToPlayer(role, player.getName(), players)) {
                    throw new TakeDrinkEvent(specialDice.getValue(), player);
                }
                return;
            case Heros:
                if (assignRoleToPlayer(role, player.getName(), players)) {
                    throw new TakeDrinkEvent(specialDice.getValue(), player);
                }
                return;
            case Dragon:
                if (assignRoleToPlayer(role, player.getName(), players)) {
                    throw new TakeDrinkEvent(specialDice.getValue(), player);
                }
                return;
            case Oracle:
                if (assignRoleToPlayer(role, player.getName(), players)) {
                    throw new TakeDrinkEvent(specialDice.getValue(), player);
                }
                return;
            case Ecuyer:
                if (assignRoleToPlayer(role, player.getName(), players)) {
                    throw new TakeDrinkEvent(specialDice.getValue(), player);
                }
                return;
            case Princesse:
                if (assignRoleToPlayer(role, player.getName(), players)) {
                    throw new TakeDrinkEvent(specialDice.getValue(), player);
                }
                return;
            case Aubergiste:
                if (assignRoleToPlayer(role, player.getName(), players)) {
                    throw new TakeDrinkEvent(specialDice.getValue(), player);
                }
                return;
            case Prisonnier:
                if (assignRoleToPlayer(role, player.getName(), players)) {
                    throw new TakeDrinkEvent(specialDice.getValue(), player);
                }
                return;
            case Attaque:
                throw new AttackEvent(specialDice.getValue());
            case AllDrink:
                throw new TakeDrinkEvent(specialDice.getValue(), players.toArray(new Player[players.size()]));
            case Drink:
                throw new GiveDrinkEvent(d2, player);

        }
    }

    private static boolean assignRoleToPlayer(Role role, String name, Collection<Player> players) {
        boolean useless = true;

        for (Player player : players) {
            if (player.getName().equals(name)) {
                useless = !player.addRole(role);
            } else {
                if (!player.hasRole(RoleUtil.getSuperRoleFromRole(role))) {
                    player.removeRole(role);
                } else {
                    return true;//tester avant si un super role existe
                }
            }
        }

        return useless;
    }

    private static void assignSuperRoleToPlayer(Role role, String name, Collection<Player> players) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                if (!player.addRole(role)) {
                    player.removeRole(role);
                    player.addRole(RoleUtil.getRoleFromSuperRole(role));
                }
            } else {
                player.removeRole(role);
                player.removeRole(RoleUtil.getRoleFromSuperRole(role));
            }
        }
    }
}
