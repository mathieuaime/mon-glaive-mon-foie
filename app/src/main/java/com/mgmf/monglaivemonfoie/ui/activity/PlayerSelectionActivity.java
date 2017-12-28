package com.mgmf.monglaivemonfoie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mgmf.monglaivemonfoie.R;

import java.util.ArrayList;

public class PlayerSelectionActivity extends Activity {

    private LinearLayout parentLayout;
    private int hint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection);
        Button buttonView = findViewById(R.id.player_button_view);
        Button playButtonView = findViewById(R.id.play_button_view);

        parentLayout = findViewById(R.id.list_players);

        createEditTextView();
        buttonView.setOnClickListener(v -> createEditTextView());

        playButtonView.setOnClickListener(v -> {
            ArrayList<String> players = new ArrayList<>();
            for (int i = 0; i < parentLayout.getChildCount(); i++) {
                if (parentLayout.getChildAt(i) instanceof EditText) {
                    EditText editText = (EditText) parentLayout.getChildAt(i);
                    String player = editText.getText().toString();
                    if (!player.equals("")) {
                        players.add(player.trim());
                    }
                }
            }

            Intent intent = new Intent(PlayerSelectionActivity.this, MainActivity.class);
            intent.putStringArrayListExtra("players", players);
            startActivity(intent);
        });
    }

    protected void createEditTextView() {
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, RelativeLayout.LayoutParams.MATCH_PARENT);

        hint++;
        EditText editText = new EditText(this);

        editText.setPadding(padding, padding, padding, padding);
        editText.setLayoutParams(params);
        editText.setBackground(ContextCompat.getDrawable(this, R.drawable.player_input));
        editText.setHint(getString(R.string.player) + " " + hint);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setId(hint);
        editText.requestFocus();

        editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        parentLayout.addView(editText, hint - 1);
    }
}
