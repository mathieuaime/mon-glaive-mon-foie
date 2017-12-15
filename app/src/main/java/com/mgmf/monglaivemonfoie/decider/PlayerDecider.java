package com.mgmf.monglaivemonfoie.decider;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.event.AttackEvent;
import com.mgmf.monglaivemonfoie.event.GiveDrinkEvent;
import com.mgmf.monglaivemonfoie.event.GodBattleEvent;
import com.mgmf.monglaivemonfoie.event.TakeDrinkEvent;
import com.mgmf.monglaivemonfoie.event.UselessDrinkEvent;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;
import com.mgmf.monglaivemonfoie.util.DiceUtil;
import com.mgmf.monglaivemonfoie.util.PlayerUtil;
import com.mgmf.monglaivemonfoie.util.RoleUtil;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Contains the logic for the players.
 *
 * @author Mathieu Aimé
 */

@SuppressLint("NewApi")
public class PlayerDecider {
    private static void assignRoleToPlayer(Role role, Player player, Dice dice1, Dice dice2, Dice specialDice, Collection<Player> players) {
        int d1 = Math.max(dice1.getValue(), dice2.getValue());
        int d2 = Math.min(dice1.getValue(), dice2.getValue());


        //reassign role if super role
        if (player.hasRole(RoleUtil.getSuperRoles())) {
            assignRoleFromSuperRoleToPlayer(player);
        }

        int numberOfThree = DiceUtil.numberOf(3, dice1, dice2, specialDice);
        //if the dices have almost one 3
        if (numberOfThree > 0) {
            if (!role.equals(Role.Prisonnier)) {
                PlayerUtil.getPlayerByRole(Role.Prisonnier, players).ifPresent(p -> new TakeDrinkEvent(numberOfThree).play(p));
                if (player.hasRole(Role.Prisonnier)) {
                    if (removeRoleToPlayer(player, Role.Prisonnier) && !role.equals(Role.Attaque)) {
                        return;
                    }
                }
            }
        }

        switch (role) {
            case Heros:
                if (player.hasRole(Role.Prisonnier, Role.Dieu)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                } else {
                    removeRoleToPlayer(player, Role.Princesse, Role.Ecuyer);
                    if (assignRoleToPlayer(player, role, players)) {
                        new UselessDrinkEvent(specialDice.getValue()).play(player);
                    }
                }
                break;
            case Princesse:
            case Ecuyer:
                if (player.hasRole(Role.Prisonnier, Role.Heros, Role.Dieu) || assignRoleToPlayer(player, role, players)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Catin:
                if (player.hasRole(Role.Prisonnier, Role.Dieu) || assignRoleToPlayer(player, role, players)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Dragon:
            case Oracle:
            case Aubergiste:
                if (player.hasRole(Role.Prisonnier) || assignRoleToPlayer(player, role, players)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Prisonnier:
                if (!PlayerUtil.isRole(role, players)) {
                    player.removeAllRoles();
                    player.addRole(role);
                    System.out.println(player.getName() + " devient " + role);
                } else if (player.hasRole(role)) {
                    System.out.println(player.getName() + ", tu restes Prisonnier, tu bois, double, bizzut");
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
                if (player.hasRole(Role.Prisonnier) || assignSuperRoleToPlayer(player, role, players)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Dieu:
                if (player.hasRole(Role.Prisonnier, Role.Dieu)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                } else {
                    if (d1 != 6) {
                        Optional<Player> oldGod = players.stream().filter(p -> p.hasRole(role)).findFirst();
                        if (oldGod.isPresent()) {
                            new GodBattleEvent().play(oldGod.get(), player);
                        } else {
                            becomeGod(player, players);
                        }
                    } else {
                        becomeGod(player, players);
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

    public static void becomeGod(Player player, Collection<Player> players) {
        boolean becomeGod = assignRoleToPlayer(player, Role.Dieu, players);
        if (!becomeGod) {
            removeRoleToPlayer(player, Role.Catin, Role.Heros, Role.Ecuyer, Role.Princesse);
        }
    }

    private static boolean assignRoleToPlayer(Player player, Role role, Collection<Player> players) {
        boolean useless = true;

        if (!PlayerUtil.isRole(RoleUtil.getSuperRoleFromRole(role), players)) {
            for (Player p : players) {
                if (p.getId() == player.getId()) {
                    useless = !assignRoleToPlayer(p, role);
                } else {
                    removeRoleToPlayer(p, role);
                }
            }
        }

        return useless;
    }

    private static boolean assignRoleToPlayer(Player player, Role role) {
        boolean add = player.addRole(role);

        if (add) {
            System.out.println(player.getName() + " devient " + role);
        }

        return add;
    }

    private static boolean assignSuperRoleToPlayer(Player player, Role role, Collection<Player> players) {
        boolean useless = player.hasRole(role);

        if (!useless) {
            assignRoleToPlayer(player, RoleUtil.getRoleFromSuperRole(role), players);
            player.removeAllRoles();
            player.addRole(role);
            System.out.println(player.getName() + " devient " + role);
            useless = false;
        }

        return useless;
    }

    private static void assignRoleFromSuperRoleToPlayer(Player player) {
        Set<Role> roles = player.getRoles();

        roles.forEach(r -> {
            removeRoleToPlayer(player, r);
            assignRoleToPlayer(player, RoleUtil.getRoleFromSuperRole(r));
        });
    }

    private static boolean removeRoleToPlayer(Player player, Role... roles) {
        boolean remove = false;

        for (Role role : roles) {
            if (player.hasRole(role)) {
                System.out.println(player.getName() + " n'est plus " + role);
                player.removeRole(role);
                remove = true;
            }
        }

        return remove;
    }

    public static void play(Player player, Dice dice1, Dice dice2, Dice specialDice, Collection<Player> players) {
        DiceUtil.roll(dice1, dice2, specialDice);

        DiceUtil.display(dice1, dice2, specialDice);

        Role newRole = RoleDecider.decideRole(dice1, dice2, specialDice);

        System.out.println("Role : " + newRole);

        assignRoleToPlayer(newRole, player, dice1, dice2, specialDice, players);
    }
}
