package com.mgmf.monglaivemonfoie.util;

import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;

import org.junit.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Tests for the Player Utility.
 *
 * @author Mathieu Aimé
 */
public class PlayerUtilityTest {
    @Test
    public void getPlayerByRole() throws Exception {
        Set<Player> playerSet = new HashSet<>();

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        playerSet.add(player1);
        playerSet.add(player2);

        for (Role role : RoleUtil.getRoles().values()) {
            when(player1.hasRole(role)).thenReturn(true);
            Optional<Player> playerByRole = PlayerUtil.getPlayerByRole(role, playerSet);

            if (playerByRole.isPresent()) {
                assertEquals(player1, playerByRole.get());
            } else {
                fail("No player with role " + role);
            }
        }

    }

}