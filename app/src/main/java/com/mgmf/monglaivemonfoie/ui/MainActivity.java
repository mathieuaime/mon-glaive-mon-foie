package com.mgmf.monglaivemonfoie.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mgmf.monglaivemonfoie.R;
import com.mgmf.monglaivemonfoie.decider.RoleDecider;
import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.model.Game;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.Collection;
import java.util.List;

/**
 * The main activity.
 *
 * @author Mathieu Aimé
 */

public class MainActivity extends Activity {

    private final Game game = new Game(5);
    TextView playerTextView;
    TextView roleTextView;
    TextView diceTextView;
    TextView listPlayerTextView;
    TextView gameTextView;
    RelativeLayout rlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rlayout = (RelativeLayout) findViewById(R.id.background);
        playerTextView = (TextView) findViewById(R.id.playerTextView);
        roleTextView = (TextView) findViewById(R.id.roleTextView);
        diceTextView = (TextView) findViewById(R.id.diceTextView);
        listPlayerTextView = (TextView) findViewById(R.id.listPlayersTextView);
        gameTextView = (TextView) findViewById(R.id.gameDisplay);

        rlayout.setOnClickListener(v -> {
            game.roll();
            updateDisplay();
            List<Event> events = game.play();
            displayEvents(events);
        });

        playerTextView.setText(String.format(getString(R.string.playerDisplay), game.getActualPlayer().getName()));
        diceTextView.setText(String.format(getString(R.string.diceDisplay), ""));
        roleTextView.setText(String.format(getString(R.string.roleDisplay), ""));
    }

    @SuppressLint("NewApi")
    private void displayEvents(Collection<Event> events) {
        gameTextView.setText("");
        events.forEach(this::displayEvent);
    }


    private void displayEvent(Event event) {
        String display = event.play();
        gameTextView.append(display);
        gameTextView.append("\n");
    }

    private void updateDisplay() {
        playerTextView.setText(String.format(getString(R.string.playerDisplay), game.getActualPlayer().getName()));
        roleTextView.setText(String.format(getString(R.string.roleDisplay), RoleDecider.decideRole(game.getDices())));
        diceTextView.setText(String.format(getString(R.string.diceDisplay), DiceUtil.displayDices(game.getDices())));
    }
}
