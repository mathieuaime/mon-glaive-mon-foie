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
        if (player.hasRole(RoleUtil.getSuperRoles().values())) {
            assignRoleFromSuperRoleToPlayer(player);
        }

        int numberOfThree = DiceUtil.numberOf(3, dice1, dice2, specialDice);
        //if the dices have almost one 3
        if (numberOfThree > 0) {
            if (!role.equals(Role.Prisonnier)) {
                if (player.hasRole(Role.Prisonnier)) {
                    if (removeRoleToPlayer(player, Role.Prisonnier) && !role.equals(Role.Attaque)) {
                        System.out.println(player.getName() + " sort de prison ! Pour fêter ça, il boit le spécial !");
                        new TakeDrinkEvent(specialDice.getValue()).play(player);
                        return;
                    }
                } else {
                    PlayerUtil.getPlayerByRole(Role.Prisonnier, players).ifPresent(p -> new TakeDrinkEvent(numberOfThree).play(p));
                }
            }
        }

        switch (role) {
            case Heros:
                if (player.hasRole(Role.Prisonnier, Role.Dieu)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                } else {
                    if (assignRoleToPlayer(player, role, players)) {
                        new UselessDrinkEvent(specialDice.getValue()).play(player);
                    } else {
                        removeRoleToPlayer(player, Role.Princesse);
                        PlayerUtil.getPlayerByRole(Role.Ecuyer, players).ifPresent(p -> removeRoleToPlayer(p, Role.Ecuyer));
                    }
                }
                break;
            case Princesse:
                if (player.hasRole(Role.Prisonnier, Role.Heros) || assignRoleToPlayer(player, role, players)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Ecuyer:
                if (player.hasRole(Role.Prisonnier, Role.Heros, Role.Dieu) || !PlayerUtil.getPlayerByRole(Role.Heros, players).isPresent() || assignRoleToPlayer(player, role, players)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Catin:
                if (player.hasRole(Role.Prisonnier, Role.Dieu) || assignRoleToPlayer(player, role, players)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                }
                break;
            case Dragon:
                if (player.hasRole(Role.Prisonnier) || assignRoleToPlayer(player, role, players)) {
                    new UselessDrinkEvent(specialDice.getValue()).play(player);
                } else {
                    new GiveDrinkEvent(d2).play(player);
                }
                break;
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
                    System.out.println(player.getName() + " devient " + role + ", pour fêter ça, il boit le spécial !");
                    new TakeDrinkEvent(specialDice.getValue()).play(player);
                } else if (player.hasRole(role)) {
                    System.out.println(player.getName() + " sort de prison ! Pour fêter ça, il boit le spécial !");
                    System.out.println(player.getName() + " rentre en prison ! Pour fêter ça, il boit le spécial !");
                    System.out.println("Bizzut");
                    new TakeDrinkEvent(2 * specialDice.getValue()).play(player);
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
                        new GiveDrinkEvent(d2).play(player);
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
            removeRoleToPlayer(player, Role.Catin, Role.Heros, Role.Ecuyer);
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
            player.addRole(role);
            System.out.println(player.getName() + " devient " + role);
            useless = false;
        }

        return useless;
    }

    private static void assignRoleFromSuperRoleToPlayer(Player player) {
        Set<Role> roles = player.getRoles();

        roles.stream().filter(RoleUtil::isSuperRole).forEach(r -> {
            removeRoleToPlayer(player, r);
            if (r.equals(Role.Demon)) {
                new GiveDrinkEvent(6).play(player);
            }
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
        Role newRole = RoleDecider.decideRole(dice1, dice2, specialDice);
        assignRoleToPlayer(newRole, player, dice1, dice2, specialDice, players);
    }
}
