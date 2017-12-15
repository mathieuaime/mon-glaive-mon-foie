package com.mgmf.monglaivemonfoie.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class for a player
 *
 * @author Mathieu Aimé
 */

public class Player {

    private static int numberOfPlayers = 1;
    private final String name;
    private long id;
    private Set<Role> roles = new HashSet<>();

    public Player(String name) {
        id = numberOfPlayers++;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean hasRole(Collection<Role> roles) {
        return hasRole(roles.toArray(new Role[roles.size()]));
    }

    public boolean hasRole(Role... roles) {
        return Arrays.stream(roles).anyMatch(this.roles::contains);
    }

    public Set<Role> getRoles() {
        return new HashSet<>(roles);
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public boolean removeRole(Role role) {
        return this.roles.remove(role);
    }

    public void removeAllRoles() {
        roles.clear();
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

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }

    public void removeRole(Role... roles) {
        Arrays.stream(roles).forEach(this::removeRole);
    }
}
