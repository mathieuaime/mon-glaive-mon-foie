package com.mgmf.monglaivemonfoie.event.action;

import com.mgmf.monglaivemonfoie.event.ActionEvent;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;
import com.mgmf.monglaivemonfoie.util.DiceUtil;
import com.mgmf.monglaivemonfoie.util.PlayerUtil;

/**
 * Event for the attack.
 *
 * @author Mathieu Aimé
 */

public class AttackEvent extends ActionEvent {

    public AttackEvent(int nb, Player... player) {
        super(nb, player);
    }

    @Override
    public String play() {
        StringBuilder builder = new StringBuilder();

        Player dieu = PlayerUtil.getPlayerByRole(Role.Dieu, players);
        Player catin = PlayerUtil.getPlayerByRole(Role.Catin, players);
        Player heros = PlayerUtil.getPlayerByRole(Role.Heros, players);
        Player oracle = PlayerUtil.getPlayerByRole(Role.Oracle, players);

        builder.append("Dieu attaque le village pour ")
                .append(DiceUtil.displayGorgees(nb));

        if (dieu != null) {

            if (catin != null) {
                addMessage(builder, "La catin s'interpose !");
                int catinDice = DiceUtil.random();
                addMessage(builder, "La catin fait " + catinDice);
                if (catinDice == 1) {
                    addMessage(builder, "DIEU EST VAINCU !!!!" + dieu.getName() + " boit " + DiceUtil.displayGorgees(nb) + " !");
                    return builder.toString();
                } else {
                    addMessage(builder, "La catin échoue lamentablement ... " + catin.getName() + " boit " + DiceUtil.displayGorgees(catinDice));
                }
            }

            if (heros != null) {
                addMessage(builder, "Le héros s'interpose !");
                if (oracle != null) {
                    addMessage(builder, "L'oracle prédit le coup du héros");
                }
                int herosDice = DiceUtil.random();
                addMessage(builder, "Le héros fait " + herosDice);
                if (herosDice == 1) {
                    addMessage(builder, "LE HEROS EST FOUDROYE !!!! " + heros.getName() + " SEC !!!! " + "\n" + dieu.getName() + " donne " + DiceUtil.displayGorgees(nb) + " !");
                    heros.removeRole(Role.Heros);
                } else if (herosDice == 6) {
                    addMessage(builder, "DIEU EST VAINCU !!!! " + dieu.getName() + " boit " + DiceUtil.displayGorgees(nb) + " !");
                    return builder.toString();
                } else if (herosDice > 3) {
                    addMessage(builder, "Le héros se sacrifie pour le village" + "\n" + heros.getName() + " boit " + DiceUtil.displayGorgees(nb));
                } else {
                    addMessage(builder, "Le héros échoue à sauver le village" + "\n" + heros.getName() + " boit " + DiceUtil.displayGorgees(herosDice) + " et " + dieu.getName() + " donne " + DiceUtil.displayGorgees(nb));
                }
            } else {
                addMessage(builder, "Il n'y a pas de héros, " + dieu.getName() + " donne " + DiceUtil.displayGorgees(nb));
            }
        } else {
            addMessage(builder, "Il n'y a pas de dieu ...");
        }

        return builder.toString();
    }

    private void addMessage(StringBuilder builder, String message) {
        if (!builder.toString().equals("")) {
            builder.append('\n');
        }
        builder.append(message);
    }
}
