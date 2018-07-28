package com.mgmf.monglaivemonfoie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mgmf.monglaivemonfoie.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);
                } catch (Exception ignored) {
                    //Ignore
                } finally {
                    Intent i = new Intent(SplashActivity.this, PlayerSelectionActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }.start();
    }
}
