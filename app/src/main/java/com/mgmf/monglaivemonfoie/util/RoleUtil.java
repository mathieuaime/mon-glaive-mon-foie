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

    private static synchronized void populateRoles() {
        if (roles == null) {
            Map<Integer, Role> r = new HashMap<>();
            r.put(11, Role.HEROS);
            r.put(21, Role.ORACLE);
            r.put(22, Role.HEROS);
            r.put(31, Role.ECUYER);
            r.put(32, Role.PRISONNIER);
            r.put(33, Role.HEROS);
            r.put(41, Role.CATIN);
            r.put(42, Role.ALL_DRINK);
            r.put(43, Role.ATTACK);
            r.put(44, Role.DIEU);
            r.put(51, Role.ALL_DRINK);
            r.put(52, Role.ATTACK);
            r.put(53, Role.AUBERGISTE);
            r.put(54, Role.PRINCESSE);
            r.put(55, Role.DIEU);
            r.put(61, Role.ATTACK);
            r.put(62, Role.DRINK);
            r.put(63, Role.DRINK);
            r.put(64, Role.DRINK);
            r.put(65, Role.DRAGON);
            r.put(66, Role.DIEU);
            r.put(111, Role.CLOCHARD);
            r.put(222, Role.DEVIN);
            r.put(333, Role.APPRENTI);
            r.put(444, Role.GOURGANDINE);
            r.put(555, Role.IMPERATRICE);
            r.put(666, Role.DEMON);

            roles = Collections.unmodifiableMap(r);
        }
    }

    private static synchronized void populateSuperRoles() {
        if(superRoles == null) {
            Map<Integer, Role> r = new HashMap<>();
            r.put(111, Role.CLOCHARD);
            r.put(222, Role.DEVIN);
            r.put(333, Role.APPRENTI);
            r.put(444, Role.GOURGANDINE);
            r.put(555, Role.IMPERATRICE);
            r.put(666, Role.DEMON);

            superRoles = Collections.unmodifiableMap(r);
        }
    }

    public static Role getRoleFromSuperRole(Role role) {
        switch (role) {
            case DEMON:
                return Role.DIEU;
            case IMPERATRICE:
                return Role.PRINCESSE;
            case GOURGANDINE:
                return Role.CATIN;
            case APPRENTI:
                return Role.ECUYER;
            case DEVIN:
                return Role.ORACLE;
            case CLOCHARD:
                return Role.HEROS;
            default:
                return role;
        }
    }

    public static Role getSuperRoleFromRole(Role role) {
        switch (role) {
            case DIEU:
                return Role.DEMON;
            case PRINCESSE:
                return Role.IMPERATRICE;
            case CATIN:
                return Role.GOURGANDINE;
            case ECUYER:
                return Role.APPRENTI;
            case ORACLE:
                return Role.DEVIN;
            case HEROS:
                return Role.CLOCHARD;
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
