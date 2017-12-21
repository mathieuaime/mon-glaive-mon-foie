package com.mgmf.monglaivemonfoie.ui;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.mgmf.monglaivemonfoie.ui.surface.MyGLSurfaceView;

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

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }


}
