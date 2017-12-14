package com.mgmf.monglaivemonfoie.decider;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.event.AttackEvent;
import com.mgmf.monglaivemonfoie.event.GiveDrinkEvent;
import com.mgmf.monglaivemonfoie.event.GodBattleEvent;
import com.mgmf.monglaivemonfoie.event.TakeDrinkEvent;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;
import com.mgmf.monglaivemonfoie.util.DiceUtil;
import com.mgmf.monglaivemonfoie.util.PlayerUtil;
import com.mgmf.monglaivemonfoie.util.RoleUtil;

import java.util.Collection;

/**
 * Created by Mathieu on 14/12/2017.
 */

public class PlayerDecider {
    @SuppressLint("NewApi")
    public static void assignRoleToPlayer(Role role, Player player, Dice dice1, Dice dice2, Dice specialDice, Collection<Player> players) {
        int d1 = Math.max(dice1.getValue(), dice2.getValue());
        int d2 = Math.min(dice1.getValue(), dice2.getValue());

        int numberOfThree = DiceUtil.numberOf(3, dice1, dice2, specialDice);
        //if the dices have almost one 3
        if (numberOfThree > 0) {
            if (!role.equals(Role.Prisonnier) && !player.hasRole(Role.Prisonnier)) {
                PlayerUtil.getPlayerByRole(Role.Prisonnier, players).ifPresent(p -> new TakeDrinkEvent(numberOfThree).play(p));
            } else {
                if (player.removeRole(Role.Prisonnier)) {
                    System.out.println(player.getName() + " n'est plus " + Role.Prisonnier);
                }
            }
        }

        switch (role) {
            case Heros:
                if (player.hasRole(Role.Prisonnier, Role.Dieu)) {
                    System.out.println(player.getName() + ", t'es inutile, tu bois");
                    new TakeDrinkEvent(specialDice.getValue()).play(player);
                } else {
                    player.removeRole(Role.Catin, Role.Princesse, Role.Ecuyer);
                    if (assignRoleToPlayer(role, player.getId(), players)) {
                        System.out.println(player.getName() + ", t'es inutile, tu bois");
                        new TakeDrinkEvent(specialDice.getValue()).play(player);
                    }
                }
                break;
            case Catin:
            case Princesse:
            case Ecuyer:
                if (player.hasRole(Role.Prisonnier, Role.Heros, Role.Dieu) || assignRoleToPlayer(role, player.getId(), players)) {
                    System.out.println(player.getName() + ", t'es inutile, tu bois");
                    new TakeDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Dragon:
            case Oracle:
            case Aubergiste:
                if (player.hasRole(Role.Prisonnier) || assignRoleToPlayer(role, player.getId(), players)) {
                    System.out.println(player.getName() + ", t'es inutile, tu bois");
                    new TakeDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Prisonnier:
                if (!PlayerUtil.isRole(role, players)) {
                    player.removeAllRoles();
                    player.addRole(role);
                    System.out.println(player.getName() + " devient " + role);
                } else if (player.hasRole(role)) {
                    System.out.println(player.getName() + ", tu restes Prisonnier, tu bois, double");
                    new TakeDrinkEvent(1).play(player);
                    new TakeDrinkEvent(1).play(player);
                }
                break;
            case Demon:
            case Imperatrice:
            case Gourgandine:
            case Apprenti:
            case Devin:
            case Clochard:
                if (player.hasRole(Role.Prisonnier) || assignRoleToPlayer(role, player.getId(), players)) {
                    System.out.println(player.getName() + ", t'es inutile, tu bois");
                    new TakeDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Dieu:
                if (player.hasRole(Role.Prisonnier, Role.Dieu)) {
                    System.out.println(player.getName() + ", t'es inutile, tu bois");
                    new TakeDrinkEvent(specialDice.getValue()).play(player);
                } else {
                    if (d1 != 6) {
                        players.stream().filter(p -> p.hasRole(role)).findFirst().ifPresent(p -> new GodBattleEvent().play(p, player));
                    }
                }
                break;
            case Attaque:
                new AttackEvent(specialDice.getValue()).play(players.toArray(new Player[players.size()]));
                break;
            case AllDrink:
                System.out.println(d1 + "" + d2 + " ! Tout le monde boit le special !");
                new TakeDrinkEvent(specialDice.getValue()).play(players.toArray(new Player[players.size()]));
                break;
            case Drink:
                new GiveDrinkEvent(d2).play(player);
                break;
        }
    }

    private static boolean assignRoleToPlayer(Role role, long id, Collection<Player> players) {
        boolean useless = true;

        if (!PlayerUtil.isRole(RoleUtil.getSuperRoleFromRole(role), players)) {
            for (Player player : players) {
                if (player.getId() == id) {
                    useless = !player.addRole(role);
                    System.out.println(player.getName() + " devient " + role);
                } else {
                    if (player.removeRole(RoleUtil.getRoleFromSuperRole(role))) {
                        System.out.println(player.getName() + " n'est plus " + role);
                    }
                }
            }
        }

        return useless;
    }

    public static void play(Player player, Dice dice1, Dice dice2, Dice specialDice, Collection<Player> players) {
        DiceUtil.roll(dice1, dice2, specialDice);

        DiceUtil.display(dice1, dice2, specialDice);

        Role newRole = RoleDecider.decideRole(dice1, dice2, specialDice);

        assignRoleToPlayer(newRole, player, dice1, dice2, specialDice, players);
    }
}
