package com.mgmf.monglaivemonfoie.event;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;
import com.mgmf.monglaivemonfoie.util.DiceUtil;
import com.mgmf.monglaivemonfoie.util.PlayerUtil;

import java.util.Optional;

/**
 * Event for the attack.
 *
 * @author Mathieu Aimé
 */

public class AttackEvent extends Event {

    public AttackEvent(int nb) {
        this.nb = nb;
    }

    @SuppressLint("NewApi")
    @Override
    public void play(Player... playerSet) {

        Optional<Player> dieu = PlayerUtil.getPlayerByRole(Role.Dieu, playerSet);

        Optional<Player> catin = PlayerUtil.getPlayerByRole(Role.Catin, playerSet);

        Optional<Player> heros = PlayerUtil.getPlayerByRole(Role.Heros, playerSet);

        Optional<Player> oracle = PlayerUtil.getPlayerByRole(Role.Oracle, playerSet);

        System.out.println("Dieu attaque le village pour " + DiceUtil.displayGorgees(nb));

        if (dieu.isPresent()) {

            if (catin.isPresent()) {
                System.out.println("La catin s'interpose !");
            } else {
                System.out.println("Il n'y a pas de catin ...");
            }

            if (heros.isPresent()) {
                System.out.println("Le héros s'interpose !");
                if (oracle.isPresent()) {
                    System.out.println("L'oracle prédit le coup du héros");
                }
            } else {
                System.out.println("Il n'y a pas de héros ...");
            }
        } else {
            System.out.println("Il n'y a pas de dieu ...");
        }

    }
}
