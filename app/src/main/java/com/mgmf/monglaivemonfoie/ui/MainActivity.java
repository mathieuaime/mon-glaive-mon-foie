package com.mgmf.monglaivemonfoie.ui;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.mgmf.monglaivemonfoie.R;
import com.mgmf.monglaivemonfoie.decider.PlayerDecider;
import com.mgmf.monglaivemonfoie.decider.RoleDecider;
import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Player;
import com.mgmf.monglaivemonfoie.util.DiceUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int NB_PLAYERS = 5;

    private Dice dice1 = new Dice();
    private Dice dice2 = new Dice();
    private Dice specialDice = new Dice(true);
    private final List<Player> players = new ArrayList<>();
    private int actualPlayer = 0;

    private TextView playerTextView;
    private TextView diceTextView;

    private TextView roleTextView;
    private TextView gameTextView;

    private TextView listPlayersTextView;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 1; i <= NB_PLAYERS; i++) {
            players.add(new Player("Player" + i));
        }

        playerTextView = (TextView) findViewById(R.id.playerTextView);
        diceTextView = (TextView) findViewById(R.id.diceTextView);

        roleTextView = (TextView) findViewById(R.id.roleTextView);
        gameTextView = (TextView) findViewById(R.id.gameDisplay);

        listPlayersTextView = (TextView) findViewById(R.id.listPlayersTextView);
        playButton = (Button) findViewById(R.id.playButton);


        playButton.setOnClickListener(view -> play());

    }

    @SuppressLint("NewApi")
    private void play() {
        Player p = players.get(actualPlayer);
        actualPlayer = (actualPlayer + 1) % NB_PLAYERS;
        gameTextView.setText("");

        Resources res = getResources();
        playerTextView.setText(String.format(res.getString(R.string.playerDisplay), p.getName()));
        DiceUtil.roll(dice1, dice2, specialDice);
        diceTextView.setText(String.format(res.getString(R.string.diceDisplay), dice1.getValue(), dice2.getValue(), specialDice.getValue()));
        roleTextView.setText(String.format(res.getString(R.string.roleDisplay), RoleDecider.decideRole(dice1, dice2, specialDice)));

        List<Event> events = PlayerDecider.play(p, dice1, dice2, specialDice, players);
        events.stream().map(e -> e.play() + '\n').forEach(e -> gameTextView.append(e));
        listPlayersTextView.setText(String.format(res.getString(R.string.playerDisplay), players));
    }
}
