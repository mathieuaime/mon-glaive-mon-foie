package com.mgmf.monglaivemonfoie.ui;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.mgmf.monglaivemonfoie.ui.surface.GameGLSurfaceView;

/**
 * The main activity.
 *
 * @author Mathieu Aimé
 */

public class MainActivity extends Activity {

    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mGLView = new GameGLSurfaceView(this);
        setContentView(mGLView);
    }
}
