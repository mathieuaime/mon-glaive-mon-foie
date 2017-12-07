package com.mgmf.monglaivemonfoie.model;

import com.mgmf.monglaivemonfoie.util.DiceUtil;
import com.mgmf.monglaivemonfoie.util.RoleUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mathieu on 06/12/2017.
 */

public class Player {
    private final String name;
    private Set<Role> roles = new HashSet<>();

    public Player() {
        this("");
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return new HashSet<>(roles);
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public boolean removeRole(Role role) {
        return roles.remove(role);
    }

    public void removeAllRoles() {
        roles.clear();
    }

    public void play(Dice dice1, Dice dice2, Dice specialDice, Map<Role, Player> rolePlayerMap) {
        DiceUtil.roll(dice1, dice2, specialDice);

        Role newRole = RoleUtil.play(dice1, dice2, specialDice);

        switch (newRole) {

        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
