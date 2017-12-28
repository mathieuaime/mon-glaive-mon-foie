package com.mgmf.monglaivemonfoie.decider;

import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.event.action.AttackEvent;
import com.mgmf.monglaivemonfoie.event.action.GodBattleEvent;
import com.mgmf.monglaivemonfoie.event.assign.BecomeEvent;
import com.mgmf.monglaivemonfoie.event.assign.UnassignEvent;
import com.mgmf.monglaivemonfoie.event.drink.GeneralDrinkEvent;
import com.mgmf.monglaivemonfoie.event.drink.GiveDrinkEvent;
import com.mgmf.monglaivemonfoie.event.drink.JailInDrinkEvent;
import com.mgmf.monglaivemonfoie.event.drink.JailOutDrinkEvent;
import com.mgmf.monglaivemonfoie.event.drink.TakeDrinkEvent;
import com.mgmf.monglaivemonfoie.event.drink.UselessDrinkEvent;
import com.mgmf.monglaivemonfoie.model.Assignment;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;
import com.mgmf.monglaivemonfoie.util.DiceUtil;
import com.mgmf.monglaivemonfoie.util.PlayerUtil;
import com.mgmf.monglaivemonfoie.util.RoleUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Contains the logic for the players.
 *
 * @author Mathieu Aimé
 */

public class PlayerDecider {
    private static List<Event> assignRoleToPlayer(Role role, Player player, Dice dice1, Dice dice2, Dice specialDice, Collection<Player> players) {

        List<Event> events = new ArrayList<>();
        int d1 = Math.max(dice1.getValue(), dice2.getValue());
        int d2 = Math.min(dice1.getValue(), dice2.getValue());

        //reassign role if super role
        if (player.hasRole(RoleUtil.getSuperRoles().values())) {
            assignRoleFromSuperRoleToPlayer(events, player);
        }

        int numberOfThree = DiceUtil.numberOf(3, dice1, dice2, specialDice);
        //if the dices have almost one 3
        if (numberOfThree > 0) {
            if (!role.equals(Role.Prisonnier)) {
                if (player.hasRole(Role.Prisonnier)) {
                    if (removeRoleToPlayer(events, player, Role.Prisonnier) && !role.equals(Role.Attaque)) {
                        events.add(new JailOutDrinkEvent(specialDice.getValue(), player));
                        return events;
                    }
                } else {
                    Player prisonier = PlayerUtil.getPlayerByRole(Role.Prisonnier, players);
                    if (prisonier != null) {
                        events.add(new TakeDrinkEvent(numberOfThree, prisonier));
                    }
                }
            }
        }

        Assignment assignment = new Assignment(player, role);
        UselessDrinkEvent uselessDrinkEvent = new UselessDrinkEvent(specialDice.getValue(), player);

        switch (role) {
            case Heros:
                if (player.hasRole(Role.Prisonnier, Role.Dieu)) {
                    events.add(uselessDrinkEvent);
                } else {
                    if (assignRoleToPlayer(events, assignment, players)) {
                        events.add(uselessDrinkEvent);
                    } else {
                        removeRoleToPlayer(events, player, Role.Princesse);
                        Player ecuyer = PlayerUtil.getPlayerByRole(Role.Ecuyer, players);
                        if (ecuyer != null) {
                            removeRoleToPlayer(events, ecuyer, Role.Ecuyer);
                        }
                    }
                }
                break;
            case Princesse:
                if (player.hasRole(Role.Prisonnier, Role.Heros) || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                }
                break;
            case Ecuyer:
                if (player.hasRole(Role.Prisonnier, Role.Heros, Role.Dieu) || PlayerUtil.getPlayerByRole(Role.Heros, players) == null || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                }
                break;
            case Catin:
                if (player.hasRole(Role.Prisonnier, Role.Dieu) || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                }
                break;
            case Dragon:
                if (player.hasRole(Role.Prisonnier) || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                } else {
                    events.add(new GiveDrinkEvent(d2, player));
                }
                break;
            case Oracle:
            case Aubergiste:
                if (player.hasRole(Role.Prisonnier) || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                }
                break;
            case Prisonnier:
                if (!PlayerUtil.isRole(role, players)) {
                    player.removeAllRoles();
                    player.addRole(role);
                    events.add(new JailInDrinkEvent(specialDice.getValue(), player));
                } else if (player.hasRole(role)) {
                    events.add(new JailOutDrinkEvent(specialDice.getValue(), player));
                    events.add(new JailInDrinkEvent(specialDice.getValue(), player));
                }
                break;
            case Demon:
            case Imperatrice:
            case Gourgandine:
            case Apprenti:
            case Devin:
            case Clochard:
                if (player.hasRole(Role.Prisonnier) || assignSuperRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                }
                break;
            case Dieu:
                if (player.hasRole(Role.Prisonnier, Role.Dieu)) {
                    events.add(uselessDrinkEvent);
                } else {
                    if (d1 != 6) {
                        Player oldGod = null;
                        for (Player p : players) {
                            if (p.hasRole(role)) {
                                oldGod = p;
                                break;
                            }
                        }
                        if (oldGod != null) {
                            events.add(new GodBattleEvent(specialDice.getValue(), oldGod, player));
                        } else {
                            becomeGod(events, player, players);
                        }
                    } else {
                        becomeGod(events, player, players);
                        events.add(new GiveDrinkEvent(d2, player));
                    }
                }
                break;
            case Attaque:
                events.add(new AttackEvent(d1, players.toArray(new Player[players.size()])));
                break;
            case AllDrink:
                events.add(new GeneralDrinkEvent(d1 * 10 + d2, specialDice.getValue()));
                break;
            case Drink:
                events.add(player.hasRole(Role.Prisonnier) ? uselessDrinkEvent : new GiveDrinkEvent(d2, player));
                break;
        }

        return events;
    }

    public static void becomeGod(List<Event> events, Player player, Collection<Player> players) {
        boolean becomeGod = assignRoleToPlayer(events, new Assignment(player, Role.Dieu), players);
        if (!becomeGod) {
            removeRoleToPlayer(events, player, Role.Catin, Role.Heros, Role.Ecuyer);
        }
    }

    private static boolean assignRoleToPlayer(List<Event> events, Assignment assignment, Collection<Player> players) {
        Player player = assignment.getPlayer();
        Role role = assignment.getRole();

        boolean useless = true;

        Role superRole = RoleUtil.getSuperRoleFromRole(role);

        if (superRole == null || !PlayerUtil.isRole(superRole, players)) {
            for (Player p : players) {
                if (p.getId() == player.getId()) {
                    useless = !assignRoleToPlayer(events, assignment);
                } else {
                    removeRoleToPlayer(events, p, role);
                }
            }
        }

        return useless;
    }

    private static boolean assignRoleToPlayer(List<Event> events, Assignment assignment) {
        Player player = assignment.getPlayer();
        Role role = assignment.getRole();

        boolean add = player.addRole(role);

        if (add) {
            events.add(new BecomeEvent(new Assignment(player, role)));
        }

        return add;
    }

    private static boolean assignSuperRoleToPlayer(List<Event> events, Assignment assignment, Collection<Player> players) {
        Player player = assignment.getPlayer();
        Role role = assignment.getRole();

        boolean useless = player.hasRole(role);

        if (!useless) {
            assignRoleToPlayer(events, new Assignment(player, RoleUtil.getRoleFromSuperRole(role)), players);
            player.addRole(role);
            events.add(new BecomeEvent(assignment));
            useless = false;
        }

        return useless;
    }

    private static void assignRoleFromSuperRoleToPlayer(List<Event> events, Player player) {
        for (Role r : player.getRoles()) {
            if (RoleUtil.isSuperRole(r)) {
                removeRoleToPlayer(events, player, r);
                if (r.equals(Role.Demon)) {
                    events.add(new GiveDrinkEvent(6, player));
                }
            }
        }
    }

    private static boolean removeRoleToPlayer(List<Event> events, Player player, Role... roles) {
        boolean remove = false;

        for (Role role : roles) {
            if (player.hasRole(role)) {
                events.add(new UnassignEvent(new Assignment(player, role)));
                player.removeRole(role);
                remove = true;
            }
        }

        return remove;
    }

    public static List<Event> play(Player player, Dice dice1, Dice dice2, Dice specialDice, Collection<Player> players) {
        return assignRoleToPlayer(RoleDecider.decideRole(dice1, dice2, specialDice), player, dice1, dice2, specialDice, players);
    }
}
