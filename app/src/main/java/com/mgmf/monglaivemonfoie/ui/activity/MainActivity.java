package com.mgmf.monglaivemonfoie.ui.activity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mgmf.monglaivemonfoie.R;
import com.mgmf.monglaivemonfoie.decider.RoleDecider;
import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.model.Dice;
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

public class MainActivity extends Activity implements SensorEventListener {

    private long lastUpdate = -1;
    private float last_x, last_y, last_z;

    private static final int UPDATE_DELAY = 50;
    private static final int SHAKE_THRESHOLD = 700;

    private boolean shake = true;

    private SensorManager sensorMgr;

    private Game game;
    private TextView playerTextView;
    private TextView roleTextView;
    private TextView gameTextView;
    private ImageView die1ImageView;
    private ImageView die2ImageView;
    private ImageView specialDieImageView;
    private ArrayAdapter adapter;
    private RelativeLayout rlayout;
    private final List<String> displayPlayers = new ArrayList<>();
    private Iterator<String> displayIterator;

    private Animation diceAnimation;
    private Animation specialDiceAnimation;

    final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Dice[] dices = game.getDices();
            die1ImageView.setImageResource(getDiceDrawable(dices[0].getValue()));
            die2ImageView.setImageResource(getDiceDrawable(dices[1].getValue()));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            die1ImageView.setImageResource(getDiceDrawable(DiceUtil.random()));
            die2ImageView.setImageResource(getDiceDrawable(DiceUtil.random()));
        }
    };

    final Animation.AnimationListener animationListenerSpecialDice = new Animation.AnimationListener() {
        private List<Event> events;

        @Override
        public void onAnimationStart(Animation animation) {
            events = game.play();
            roleTextView.setText("");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            specialDieImageView.setImageResource(getDiceDrawable(game.getDices()[2].getValue()));
            updateDisplay();
            displayEvents(events);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            specialDieImageView.setImageResource(getDiceDrawable(DiceUtil.random()));
        }
    };

    private final View.OnClickListener gameListener = v -> play();

    private void play() {
        game.roll();
        die1ImageView.startAnimation(diceAnimation);
        die2ImageView.startAnimation(diceAnimation);
        specialDieImageView.startAnimation(specialDiceAnimation);
    }

    private int getDiceDrawable(int die) {
        return getResources().getIdentifier("die" + die, "drawable", getPackageName());
    }

    private final View.OnClickListener eventListener = v -> displayEventIterator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = new Game(getIntent().getStringArrayListExtra("players"));

        setContentView(R.layout.activity_main);

        playerTextView = findViewById(R.id.playerTextView);
        roleTextView = findViewById(R.id.role);
        gameTextView = findViewById(R.id.gameDisplay);

        die1ImageView = findViewById(R.id.die1);
        die2ImageView = findViewById(R.id.die2);
        specialDieImageView = findViewById(R.id.specialDie);

        adapter = new CustomAdaptater<>(MainActivity.this, R.layout.custom_row, displayPlayers);

        rlayout = findViewById(R.id.background);

        rlayout.setOnClickListener(gameListener);

        playerTextView.setText(game.getActualPlayer().getName());
        roleTextView.setText("");

        diceAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
        specialDiceAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);

        diceAnimation.setAnimationListener(animationListener);
        specialDiceAnimation.setAnimationListener(animationListenerSpecialDice);

        ListView listPlayerTextView = findViewById(R.id.listPlayersTextView);
        listPlayerTextView.setAdapter(adapter);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        boolean accelSupported = sensorMgr.registerListener(this, sensorMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        if (!accelSupported) sensorMgr.unregisterListener(this); //no accelerometer on the device

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
            shake = false;
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
            shake = true;
            rlayout.setOnClickListener(gameListener);
            updatePlayersDisplay();
        }
    }

    private void updateDisplay() {
        playerTextView.setText(game.getActualPlayer().getName());
        roleTextView.setText(RoleDecider.decideRole(game.getDices()).toString());
    }

    private void updatePlayersDisplay() {
        List<Player> players = game.getPlayers();
        StringBuilder rowBuilder = new StringBuilder();
        displayPlayers.clear();

        for (Player p : players) {
            rowBuilder.setLength(0);
            rowBuilder.append(p.getName()).append(" :");
            for (Role role : p.getRoles()) {
                rowBuilder.append(" ").append(role);
            }

            displayPlayers.add(rowBuilder.toString());
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > UPDATE_DELAY) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                if (speed > SHAKE_THRESHOLD && shake) { //the screen was shaked
                    play();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
