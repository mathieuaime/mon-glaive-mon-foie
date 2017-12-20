package com.mgmf.monglaivemonfoie.ui;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Game;
import com.mgmf.monglaivemonfoie.ui.renderer.MyGLRenderer;

import java.util.Collection;
import java.util.List;

/**
 * Created by ranaivoson on 20/12/17.
 */

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private final Game game;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        game = new Game(5);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Roll");
                game.roll();
                displayDice();
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("Play");
                //List<Event> events = game.play();
                //displayEvents(events);
                break;
        }

        return true;
    }

    private void displayEvents(Collection<Event> events) {
        events.stream().map(e -> e.play() + '\n').forEach(System.out::println);
    }

    private void displayDice() {
        for (Dice dice : game.getDices()) {
            System.out.print(dice.getValue() + " ");
        }
        System.out.println();
    }
}
