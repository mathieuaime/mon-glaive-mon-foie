package com.mgmf.monglaivemonfoie.util;

import com.mgmf.monglaivemonfoie.model.Role;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for the roles.
 *
 * @author Mathieu Aimé
 */

public class RoleUtil {

    private RoleUtil() {
    }

    private static Map<Integer, Role> roles = null;
    private static Map<Integer, Role> superRoles = null;

    private synchronized static void populateRoles() {
        if (roles == null) {
            Map<Integer, Role> r = new HashMap<>();
            r.put(11, Role.Heros);
            r.put(21, Role.Oracle);
            r.put(22, Role.Heros);
            r.put(31, Role.Ecuyer);
            r.put(32, Role.Prisonnier);
            r.put(33, Role.Heros);
            r.put(41, Role.Catin);
            r.put(42, Role.AllDrink);
            r.put(43, Role.Attaque);
            r.put(44, Role.Dieu);
            r.put(51, Role.AllDrink);
            r.put(52, Role.Attaque);
            r.put(53, Role.Aubergiste);
            r.put(54, Role.Princesse);
            r.put(55, Role.Dieu);
            r.put(61, Role.Attaque);
            r.put(62, Role.Drink);
            r.put(63, Role.Drink);
            r.put(64, Role.Drink);
            r.put(65, Role.Dragon);
            r.put(66, Role.Dieu);
            r.put(111, Role.Clochard);
            r.put(222, Role.Devin);
            r.put(333, Role.Apprenti);
            r.put(444, Role.Gourgandine);
            r.put(555, Role.Imperatrice);
            r.put(666, Role.Demon);

            roles = Collections.unmodifiableMap(r);
        }
    }

    private synchronized static void populateSuperRoles() {
        if(superRoles == null) {
            Map<Integer, Role> r = new HashMap<>();
            r.put(111, Role.Clochard);
            r.put(222, Role.Devin);
            r.put(333, Role.Apprenti);
            r.put(444, Role.Gourgandine);
            r.put(555, Role.Imperatrice);
            r.put(666, Role.Demon);

            superRoles = Collections.unmodifiableMap(r);
        }
    }

    public static Role getRoleFromSuperRole(Role role) {
        switch (role) {
            case Demon:
                return Role.Dieu;
            case Imperatrice:
                return Role.Princesse;
            case Gourgandine:
                return Role.Catin;
            case Apprenti:
                return Role.Ecuyer;
            case Devin:
                return Role.Oracle;
            case Clochard:
                return Role.Heros;
            default:
                return role;
        }
    }

    public static Role getSuperRoleFromRole(Role role) {
        switch (role) {
            case Dieu:
                return Role.Demon;
            case Princesse:
                return Role.Imperatrice;
            case Catin:
                return Role.Gourgandine;
            case Ecuyer:
                return Role.Apprenti;
            case Oracle:
                return Role.Devin;
            case Heros:
                return Role.Clochard;
            default:
                return null;
        }
    }

    public static Map<Integer, Role> getRoles() {
        populateRoles();
        return roles;
    }

    public static Map<Integer, Role> getSuperRoles() {
        populateSuperRoles();
        return superRoles;
    }

    public static boolean isSuperRole(Role role) {
        return getSuperRoles().values().contains(role);
    }
}
