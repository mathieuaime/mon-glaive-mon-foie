package com.mgmf.monglaivemonfoie.decider;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.mgmf.monglaivemonfoie.App;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Role;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Tests for the Role Decider.
 *
 * @author Mathieu Aimé
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(App.class)
public class RoleDeciderTest {

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
    public void decideSuperRole() throws Exception {
        Dice dice1 = mock(Dice.class);
        Dice dice2 = mock(Dice.class);
        Dice specialDice = mock(Dice.class);

        Map<Integer, Role> roles = new HashMap<>();
        roles.put(1, Role.Clochard);
        roles.put(2, Role.Devin);
        roles.put(3, Role.Apprenti);
        roles.put(4, Role.Gourgandine);
        roles.put(5, Role.Imperatrice);
        roles.put(6, Role.Demon);

        for (int i = 1; i <= 6; ++i) {
            when(dice1.getValue()).thenReturn(i);
            when(dice2.getValue()).thenReturn(i);
            when(specialDice.getValue()).thenReturn(i);

            assertEquals(RoleDecider.decideRole(dice1, dice2, specialDice), roles.get(i));
        }
    }

    @Test
    public void decideRole() throws Exception {
        Dice dice1 = mock(Dice.class);
        Dice dice2 = mock(Dice.class);
        Dice specialDice = mock(Dice.class);

        Map<Integer, Role> roles = new HashMap<>();
        roles.put(21, Role.Heros);
        roles.put(21, Role.Oracle);
        roles.put(22, Role.Heros);
        roles.put(31, Role.Ecuyer);
        roles.put(32, Role.Prisonnier);
        roles.put(33, Role.Heros);
        roles.put(41, Role.Catin);
        roles.put(42, Role.AllDrink);
        roles.put(43, Role.Attaque);
        roles.put(44, Role.Dieu);
        roles.put(51, Role.AllDrink);
        roles.put(52, Role.Attaque);
        roles.put(53, Role.Aubergiste);
        roles.put(54, Role.Princesse);
        roles.put(55, Role.Dieu);
        roles.put(61, Role.Attaque);
        roles.put(62, Role.Drink);
        roles.put(63, Role.Drink);
        roles.put(64, Role.Drink);
        roles.put(65, Role.Dragon);
        roles.put(66, Role.Dieu);

        for (int i = 6; i > 0; --i) {
            for (int j = i - 1; j > 0; --j) {
                when(dice1.getValue()).thenReturn(i);
                when(dice2.getValue()).thenReturn(j);
                when(specialDice.getValue()).thenReturn(i == 1 ? 2 : 1);

                assertEquals(roles.get(10 * i + j), RoleDecider.decideRole(dice1, dice2, specialDice));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void decideRoleWithNotEnoughtDice() throws Exception {
        Dice dice1 = mock(Dice.class);
        Dice dice2 = mock(Dice.class);

        RoleDecider.decideRole(dice1, dice2);
    }

}