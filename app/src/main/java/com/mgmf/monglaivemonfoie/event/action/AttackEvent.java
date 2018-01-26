package com.mgmf.monglaivemonfoie.event.action;

import android.content.Context;

import com.mgmf.monglaivemonfoie.App;
import com.mgmf.monglaivemonfoie.R;
import com.mgmf.monglaivemonfoie.event.ActionEvent;
import com.mgmf.monglaivemonfoie.event.Event;
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

        Player apprenti = PlayerUtil.getPlayerByRole(Role.Apprenti, players);

        String nbDrink = DiceUtil.displayGorgees(nb);
        Context context = App.getAppContext();

        builder.append(String.format(context.getString(R.string.godAttack), nbDrink));

        if (dieu != null) {

            if (catin != null) {
                addMessage(builder, context.getString(R.string.catinIntervention));
                int catinDice = DiceUtil.random();
                addMessage(builder, String.format(context.getString(R.string.catinResult), catinDice));
                if (catinDice == 1) {
                    addMessage(builder, String.format(context.getString(R.string.heroWin), dieu.getName(), nbDrink));
                    takeDrinkIfExist(builder, apprenti, nb, context);
                    return builder.toString();
                } else {
                    addMessage(builder, String.format(context.getString(R.string.catinFail), catin.getName(), DiceUtil.displayGorgees(catinDice)));
                    takeDrinkIfExist(builder, apprenti, catinDice, context);
                }
            }

            if (heros != null) {

                addMessage(builder, context.getString(R.string.heroIntervention));
                int oraclePrediction = 0;
                if (oracle != null) {
                    addMessage(builder, context.getString(R.string.oraclePrediction));
                    oraclePrediction = DiceUtil.random();
                    addMessage(builder, "L'oracle prédit " + oraclePrediction);
                }

                int herosDice = DiceUtil.random();

                addMessage(builder, String.format(context.getString(R.string.heroResult), herosDice));

                if (oraclePrediction != 0) {
                    if (herosDice == oraclePrediction) {
                        addMessage(builder, "L'oracle a prédit juste ! " + oracle.getName() + " donne " + DiceUtil.displayGorgees(oraclePrediction));
                        takeDrinkIfExist(builder, apprenti, oraclePrediction, context);
                    } else {
                        addMessage(builder, "L'oracle oriente le coup du héros");
                        herosDice += Integer.compare(oraclePrediction, herosDice);
                        addMessage(builder, String.format(context.getString(R.string.heroResult), herosDice));
                    }
                }

                if (herosDice == 1) {
                    addMessage(builder, String.format(context.getString(R.string.godWin), heros.getName(), dieu.getName(), nbDrink));
                    takeDrinkIfExist(builder, apprenti, nb, context);
                    heros.removeRole(Role.Heros);
                } else if (herosDice == 6) {
                    addMessage(builder, String.format(context.getString(R.string.heroWin), dieu.getName(), nbDrink));
                    takeDrinkIfExist(builder, apprenti, nb, context);
                    return builder.toString();
                } else if (herosDice > 3) {
                    addMessage(builder, String.format(context.getString(R.string.godFail), dieu.getName(), nbDrink));
                    takeDrinkIfExist(builder, apprenti, nb, context);
                } else {
                    addMessage(builder, String.format(context.getString(R.string.heroFail), heros.getName(), nbDrink, dieu.getName(), nbDrink));
                    takeDrinkIfExist(builder, apprenti, nb*2, context);
                }
            } else {
                addMessage(builder, String.format(context.getString(R.string.noHero), dieu.getName(), nbDrink));
                takeDrinkIfExist(builder, apprenti, nb, context);
            }
        } else {
            addMessage(builder, context.getString(R.string.noGod));
        }

        return builder.toString();
    }

    private void takeDrinkIfExist(StringBuilder builder, Player apprenti, int nbDrink, Context context) {
        if(apprenti != null) {
            addMessage(builder, String.format(context.getString(R.string.takeDrink), apprenti.getName(), DiceUtil.displayGorgees(nbDrink)));
        }
    }

    private void addMessage(StringBuilder builder, String message) {
        if (!builder.toString().equals("")) {
            builder.append('\n');
        }
        builder.append(message);
    }
}
