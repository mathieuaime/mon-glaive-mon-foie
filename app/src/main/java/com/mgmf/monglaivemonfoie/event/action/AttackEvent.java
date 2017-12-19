package com.mgmf.monglaivemonfoie.event.action;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.event.ActionEvent;
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

public class AttackEvent extends ActionEvent {

    public AttackEvent(int nb, Player... player) {
        super(nb, player);
    }

    @SuppressLint("NewApi")
    @Override
    public String play() {
        StringBuilder builder = new StringBuilder();

        Optional<Player> dieu = PlayerUtil.getPlayerByRole(Role.Dieu, players);

        Optional<Player> catin = PlayerUtil.getPlayerByRole(Role.Catin, players);

        Optional<Player> heros = PlayerUtil.getPlayerByRole(Role.Heros, players);

        Optional<Player> oracle = PlayerUtil.getPlayerByRole(Role.Oracle, players);

        builder.append("Dieu attaque le village pour ").append(DiceUtil.displayGorgees(nb)).append('\n');

        if (dieu.isPresent()) {

            builder.append(catin.isPresent() ? "La catin s'interpose !" : "Il n'y a pas de catin ...").append('\n');

            if (heros.isPresent()) {
                builder.append("Le héros s'interpose !").append('\n');
                if (oracle.isPresent()) {
                    builder.append("L'oracle prédit le coup du héros").append('\n');
                }
            } else {
                builder.append("Il n'y a pas de héros ...").append('\n');
            }
        } else {
            builder.append("Il n'y a pas de dieu ...").append('\n');
        }

        return builder.toString();
    }
}
