package com.mgmf.monglaivemonfoie.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mgmf.monglaivemonfoie.R;
import com.mgmf.monglaivemonfoie.decider.RoleDecider;
import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.model.Game;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.model.Role;
import com.mgmf.monglaivemonfoie.ui.adaptater.CustomAdaptater;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The main activity.
 *
 * @author Mathieu Aimé
 */

public class MainActivity extends Activity {

    private Game game;
    private TextView playerTextView;
    private TextView roleTextView;
    private TextView diceTextView;
    private TextView gameTextView;
    private ArrayAdapter adapter;
    private RelativeLayout rlayout;
    private final List<String> displayPlayers = new ArrayList<>();
    private Iterator<String> displayIterator;

    private final View.OnClickListener gameListener = v -> {
        game.roll();
        updateDisplay();
        List<Event> events = game.play();
        displayEvents(events);
    };

    private final View.OnClickListener eventListener = v -> displayEventIterator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = new Game(getIntent().getStringArrayListExtra("players"));

        setContentView(R.layout.activity_main);

        playerTextView = findViewById(R.id.playerTextView);
        roleTextView = findViewById(R.id.roleTextView);
        diceTextView = findViewById(R.id.diceTextView);
        gameTextView = findViewById(R.id.gameDisplay);

        adapter = new CustomAdaptater<>(MainActivity.this, R.layout.custom_row, displayPlayers);

        rlayout = findViewById(R.id.background);

        rlayout.setOnClickListener(gameListener);

        playerTextView.setText(game.getActualPlayer().getName());
        diceTextView.setText(String.format(getString(R.string.diceDisplay), ""));
        roleTextView.setText(String.format(getString(R.string.roleDisplay), ""));

        ListView listPlayerTextView = findViewById(R.id.listPlayersTextView);
        listPlayerTextView.setAdapter(adapter);

        updatePlayersDisplay();
    }

    private void displayEvents(Collection<Event> events) {
        gameTextView.setText("");
        for (Event event : events) {
            displayEvent(event);
        }
    }

    private void displayEvent(Event event) {
        String display = event.play();
        String[] displays = display.split("\n");
        if (displays.length > 1) {
            this.displayIterator = Arrays.asList(displays).iterator();
            rlayout.setOnClickListener(eventListener);
            displayEventIterator();
        } else if (displays.length == 1) {
            if (gameTextView.getText().length() > 0) {
                gameTextView.append("\n");
            }
            gameTextView.append(displays[0]);
            updatePlayersDisplay();
        }
    }

    private void displayEventIterator() {
        if (displayIterator.hasNext()) {
            String display = displayIterator.next();
            if (!display.equals("")) {
                gameTextView.setText("");
                gameTextView.append(display);
            }
        }

        if (!displayIterator.hasNext()) {
            rlayout.setOnClickListener(gameListener);
            updatePlayersDisplay();
        }
    }

    private void updateDisplay() {
        playerTextView.setText(game.getActualPlayer().getName());
        diceTextView.setText(String.format(getString(R.string.diceDisplay), DiceUtil.displayDices(game.getDices())));
        roleTextView.setText(String.format(getString(R.string.roleDisplay), RoleDecider.decideRole(game.getDices())));
    }

    private void updatePlayersDisplay() {
        List<Player> players = game.getPlayers();
        StringBuilder rowBuilder = new StringBuilder();
        displayPlayers.clear();

        for (Player p : players) {
            rowBuilder.setLength(0);
            rowBuilder.append(p.getName())
                    .append(" :");
            for (Role role : p.getRoles()) {
                rowBuilder.append(" ")
                        .append(role);
            }

            displayPlayers.add(rowBuilder.toString());
        }

        adapter.notifyDataSetChanged();
    }
}
