package com.mgmf.monglaivemonfoie.model;

/**
 * Model class for a assignment of a role to a player
 *
 * @author Mathieu Aimé
 */

public class Assignment {
    private Player player;
    private Role role;

    public Assignment(Player player, Role role) {
        this.player = player;
        this.role = role;
    }

    public Player getPlayer() {
        return player;
    }

    public Role getRole() {
        return role;
    }
}
