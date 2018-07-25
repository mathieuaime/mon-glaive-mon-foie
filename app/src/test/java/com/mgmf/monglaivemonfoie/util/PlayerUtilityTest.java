package com.mgmf.monglaivemonfoie.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;

import com.mgmf.monglaivemonfoie.App;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit Tests for the Player Utility.
 *
 * @author Mathieu Aimé
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(App.class)
public class PlayerUtilityTest {

    @Mock
    private Context mockApplicationContext;
    @Mock
    private Resources mockContextResources;
    @Mock
    private SharedPreferences mockSharedPreferences;

    @Before
    public void setupTests() {
        MockitoAnnotations.initMocks(this);

        PowerMockito.mockStatic(App.class);
        PowerMockito.when(App.getAppContext()).thenReturn(mockApplicationContext);
        when(mockApplicationContext.getResources()).thenReturn(mockContextResources);
        when(mockApplicationContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);
        when(mockContextResources.getString(anyInt())).thenReturn("mocked string");
    }

    @Test
    public void getPlayerByRole() throws Exception {
        Set<Player> playerSet = new HashSet<>();

        Player player1 = new Player("player1");
        Player player2 = new Player("player2");

        playerSet.add(player1);
        playerSet.add(player2);

        for (Role role : RoleUtil.getRoles().values()) {
            player1.removeAllRoles();
            player1.addRole(role);
            Player playerByRole = PlayerUtil.getPlayerByRole(role, playerSet);

            if (playerByRole != null) {
                assertEquals(player1, playerByRole);
            } else {
                fail("No player with role " + role);
            }
        }
    }
}