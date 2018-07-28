package com.mgmf.monglaivemonfoie.decider;

import android.content.Context;

import com.mgmf.monglaivemonfoie.App;
import com.mgmf.monglaivemonfoie.R;
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
import com.mgmf.monglaivemonfoie.model.Dices;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;
import com.mgmf.monglaivemonfoie.util.DiceUtil;
import com.mgmf.monglaivemonfoie.util.PlayerUtil;
import com.mgmf.monglaivemonfoie.util.RoleUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Contains the logic for the players.
 *
 * @author Mathieu Aimé
 */

public class PlayerDecider {

    private PlayerDecider() {
    }

    private static List<Event> assignRoleToPlayer(Role role, Player player, Dices dices, Collection<Player> players) {

        List<Event> events = new ArrayList<>();

        playDevinRole(dices, players, events);

        Player apprenti = PlayerUtil.getPlayerByRole(Role.APPRENTI, players);
        Player imperatrice = PlayerUtil.getPlayerByRole(Role.IMPERATRICE, players);
        Player gourgandine = PlayerUtil.getPlayerByRole(Role.GOURGANDINE, players);

        //reassign role if super role
        if (player.hasRole(RoleUtil.getSuperRoles().values())) {
            assignRoleFromSuperRoleToPlayer(events, player, apprenti, imperatrice, gourgandine);
        }

        //reset in case they're lost their role in the beginning of the tour
        apprenti = PlayerUtil.getPlayerByRole(Role.APPRENTI, players);
        imperatrice = PlayerUtil.getPlayerByRole(Role.IMPERATRICE, players);
        gourgandine = PlayerUtil.getPlayerByRole(Role.GOURGANDINE, players);

        //Super role
        Player clochard = PlayerUtil.getPlayerByRole(Role.CLOCHARD, players);
        Player demon = PlayerUtil.getPlayerByRole(Role.DEMON, players);
        boolean endTurn = playPrisonierRole(role, player, dices, players, events, apprenti);

        if (!endTurn) {
            Dice specialDice = dices.getSpecialDice();
            takeDrinkIfExist(events, clochard, specialDice.getValue());

            if (demon != null) {
                events.add(new GiveDrinkEvent(specialDice.getValue(), demon));
                takeDrinkIfExist(events, apprenti, specialDice.getValue());
                giveDrinkIfExist(events, imperatrice, specialDice.getValue());
                playGourgandineRole(events, player, gourgandine);
            }

            applyRole(role, player, players, events, dices, apprenti, imperatrice, gourgandine);
        }

        return events;
    }

    private static void applyRole(Role role, Player player, Collection<Player> players, List<Event> events, Dices dices, Player apprenti, Player imperatrice, Player gourgandine) {
        boolean isPrisoner = player.hasRole(Role.PRISONNIER);
        int specialDice = dices.getSpecialDice().getValue();
        int d1 = dices.getGreater().getValue();
        int d2 = dices.getLower().getValue();

        Assignment assignment = new Assignment(player, role);
        UselessDrinkEvent uselessDrinkEvent = new UselessDrinkEvent(specialDice, player);

        switch (role) {
            case HEROS:
                applyHeroRole(player, players, events, specialDice, apprenti, assignment, uselessDrinkEvent);
                break;
            case PRINCESSE:
                if (player.hasRole(Role.PRISONNIER, Role.HEROS) || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                    takeDrinkIfExist(events, apprenti, 1);
                }
                break;
            case ECUYER:
                if (player.hasRole(Role.PRISONNIER, Role.HEROS, Role.DIEU) || PlayerUtil.getPlayerByRole(Role.HEROS, players) == null || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                    takeDrinkIfExist(events, apprenti, 1);
                }
                break;
            case CATIN:
                if (player.hasRole(Role.PRISONNIER, Role.DIEU) || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                    takeDrinkIfExist(events, apprenti, 1);
                }
                break;
            case DRAGON:
                if (isPrisoner || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                    takeDrinkIfExist(events, apprenti, 1);
                } else {
                    events.add(new GiveDrinkEvent(d2, player));
                    takeDrinkIfExist(events, apprenti, d2);
                    giveDrinkIfExist(events, imperatrice, d2);
                    playGourgandineRole(events, player, gourgandine);
                }
                break;
            case ORACLE:
            case AUBERGISTE:
                if (isPrisoner || assignRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                    takeDrinkIfExist(events, apprenti, 1);
                }
                break;
            case PRISONNIER:
                if (!PlayerUtil.existRole(role, players)) {
                    player.removeAllRoles();
                    player.addRole(role);
                    events.add(new JailInDrinkEvent(specialDice, player));
                    takeDrinkIfExist(events, apprenti, specialDice);
                } else if (player.hasRole(role)) {
                    events.add(new JailOutDrinkEvent(specialDice, player));
                    takeDrinkIfExist(events, apprenti, specialDice);
                    events.add(new JailInDrinkEvent(specialDice, player));
                    takeDrinkIfExist(events, apprenti, specialDice);
                }
                break;
            case DEMON:
            case IMPERATRICE:
            case GOURGANDINE:
            case APPRENTI:
            case DEVIN:
            case CLOCHARD:
                if (isPrisoner || assignSuperRoleToPlayer(events, assignment, players)) {
                    events.add(uselessDrinkEvent);
                    takeDrinkIfExist(events, apprenti, 1);
                }
                break;
            case DIEU:
                if (player.hasRole(Role.PRISONNIER, Role.DIEU)) {
                    events.add(uselessDrinkEvent);
                    takeDrinkIfExist(events, apprenti, 1);
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
                            events.add(new GodBattleEvent(oldGod, player, apprenti));
                        } else {
                            becomeGod(events, player, players);
                        }
                    } else {
                        becomeGod(events, player, players);
                        events.add(new GiveDrinkEvent(d2, player));
                        takeDrinkIfExist(events, apprenti, d2);
                        giveDrinkIfExist(events, imperatrice, d2);
                        playGourgandineRole(events, player, gourgandine);
                    }
                }
                break;
            case ATTACK:
                events.add(new AttackEvent(d1, players.toArray(new Player[players.size()])));
                break;
            case ALL_DRINK:
                events.add(new GeneralDrinkEvent(d1 * 10 + d2, specialDice));
                takeDrinkIfExist(events, apprenti, specialDice * (players.size() - 1));
                break;
            case DRINK:
                events.add(isPrisoner ? uselessDrinkEvent : new GiveDrinkEvent(d2, player));
                takeDrinkIfExist(events, apprenti, isPrisoner ? 1 : d2);
                if (!isPrisoner) {
                    giveDrinkIfExist(events, imperatrice, d2);
                    playGourgandineRole(events, player, gourgandine);
                }
                break;
        }
    }

    private static void applyHeroRole(Player player, Collection<Player> players, List<Event> events, int specialDice, Player apprenti, Assignment assignment, UselessDrinkEvent uselessDrinkEvent) {
        if (player.hasRole(Role.PRISONNIER, Role.DIEU)) {
            events.add(uselessDrinkEvent);
            takeDrinkIfExist(events, apprenti, specialDice);
        } else {
            if (assignRoleToPlayer(events, assignment, players)) {
                events.add(uselessDrinkEvent);
                takeDrinkIfExist(events, apprenti, 1);
            } else {
                removeRoleToPlayer(events, player, Role.PRINCESSE);
                Player ecuyer = PlayerUtil.getPlayerByRole(Role.ECUYER, players);
                if (ecuyer != null) {
                    removeRoleToPlayer(events, ecuyer, Role.ECUYER);
                }
            }
        }
    }

    private static boolean playPrisonierRole(Role role, Player player, Dices dices, Collection<Player> players, List<Event> events, Player apprenti) {
        Dice specialDice = dices.getSpecialDice();
        //Prisoner
        int numberOfThree = DiceUtil.numberOf(3, dices.getDice1(), dices.getDice2(), specialDice);
        //if the dices have almost one 3
        if (numberOfThree > 0 && !role.equals(Role.PRISONNIER)) {
            if (player.hasRole(Role.PRISONNIER) && !(numberOfThree == 1 && specialDice.getValue() == 3)) {
                if (removeRoleToPlayer(events, player, Role.PRISONNIER) && !role.equals(Role.ATTACK)) {
                    events.add(new JailOutDrinkEvent(specialDice.getValue(), player));
                    return true;
                }
            } else {
                Player prisonier = PlayerUtil.getPlayerByRole(Role.PRISONNIER, players);
                if (prisonier != null) {
                    events.add(new TakeDrinkEvent(numberOfThree, prisonier));
                    takeDrinkIfExist(events, apprenti, numberOfThree);
                }
            }
        }
        return false;
    }

    private static void playDevinRole(Dices dices, Collection<Player> players, List<Event> events) {
        Player devin = PlayerUtil.getPlayerByRole(Role.DEVIN, players);

        if (devin != null) {
            events.add(new Event() {
                @Override
                public String play() {
                    StringBuilder builder = new StringBuilder();

                    int die = DiceUtil.random();
                    Dice[] dice = {dices.getDice1(), dices.getDice2(), dices.getSpecialDice()};
                    Dice dieToModify = dice[new Random().nextInt(dice.length)];

                    Context context = App.getAppContext();

                    builder.append(context.getString(R.string.devinAction)).append(NEW_LINE);
                    int compare = Integer.compare(die, dieToModify.getValue());

                    if (compare == 0) {
                        builder.append(context.getString(R.string.noDiceModification));
                    } else {
                        builder.append(String.format(context.getString(R.string.diceModification), dieToModify.getValue(), dieToModify.getValue() + compare));
                    }

                    dieToModify.modifyValue(die);
                    return builder.toString();
                }
            });
        }
    }

    private static void playGourgandineRole(List<Event> events, Player player, Player gourgandine) {
        if (gourgandine != null) {
            events.add(new Event() {
                @Override
                public String play() {
                    StringBuilder builder = new StringBuilder();
                    int gourgandineDie = DiceUtil.random();
                    Context context = App.getAppContext();

                    return builder.append(context.getString(R.string.gourgandineIntervention))
                            .append(NEW_LINE)
                            .append(String.format(context.getString(R.string.actionBattle), gourgandine.getName(), gourgandineDie))
                            .append(NEW_LINE)
                            .append(String.format(context.getString(R.string.takeDrink), gourgandineDie == 1 ? player.getName() : gourgandine.getName(), DiceUtil.displayGorgees(gourgandineDie)))
                            .toString();
                }
            });
        }
    }

    private static void giveDrinkIfExist(List<Event> events, Player player, int nb) {
        if (player != null) {
            events.add(new GiveDrinkEvent(nb, player));
        }
    }

    public static void takeDrinkIfExist(List<Event> events, Player player, int nb) {
        if (player != null) {
            events.add(new TakeDrinkEvent(nb, player));
        }
    }

    public static void becomeGod(List<Event> events, Player player, Collection<Player> players) {
        boolean becomeGod = assignRoleToPlayer(events, new Assignment(player, Role.DIEU), players);
        if (!becomeGod) {
            removeRoleToPlayer(events, player, Role.CATIN, Role.HEROS, Role.ECUYER);
        }
    }

    private static boolean assignRoleToPlayer(List<Event> events, Assignment assignment, Collection<Player> players) {
        Player player = assignment.getPlayer();
        Role role = assignment.getRole();

        boolean useless = true;

        Role superRole = RoleUtil.getSuperRoleFromRole(role);

        if (superRole == null || !PlayerUtil.existRole(superRole, players)) {
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
            Player player1 = PlayerUtil.getPlayerByRole(role, players);
            if (player1 != null) {
                player1.removeRole(role);
                events.add(new UnassignEvent(new Assignment(player1, role)));

                if (role == Role.DEMON) {
                    events.add(new TakeDrinkEvent(666, player1) {
                        @Override
                        public String play() {
                            return super.play() + "\nParce que tu t'es bien fait ken sur ce coup là";
                        }
                    });
                }
            }
            player.removeAllRoles();
            assignRoleToPlayer(events, new Assignment(player, RoleUtil.getRoleFromSuperRole(role)), players);
            player.addRole(role);
            events.add(new BecomeEvent(assignment));
            useless = false;
        }

        return useless;
    }

    private static void assignRoleFromSuperRoleToPlayer(List<Event> events, Player player, Player apprenti, Player imperatrice, Player gourgandine) {
        for (Role r : player.getRoles()) {
            if (RoleUtil.isSuperRole(r)) {
                removeRoleToPlayer(events, player, r);
                if (r.equals(Role.DEMON)) {
                    events.add(new GiveDrinkEvent(6, player));
                    takeDrinkIfExist(events, apprenti, 6);
                    giveDrinkIfExist(events, imperatrice, 6);
                    playGourgandineRole(events, player, gourgandine);
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

    public static List<Event> play(Player player, Dices dices, Collection<Player> players) {
        return assignRoleToPlayer(RoleDecider.decideRole(dices), player, dices, players);
    }
}
