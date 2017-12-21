package com.mgmf.monglaivemonfoie.ui.surface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.mgmf.monglaivemonfoie.event.Event;
import com.mgmf.monglaivemonfoie.model.Dice;
import com.mgmf.monglaivemonfoie.model.Game;
import com.mgmf.monglaivemonfoie.ui.renderer.GameGLRenderer;

import java.util.Collection;

/**
 * Custom surface view.
 *
 * @author Mathieu Aimé
 */

public class GameGLSurfaceView extends GLSurfaceView {

    private static final int MARGE = 200;

    private final GameGLRenderer mRenderer;
    private final Game game;

    private boolean push;

    public GameGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new GameGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        game = new Game(5);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean isClickable = e.getX() > MARGE & e.getX() < getWidth() - MARGE && e.getY() > MARGE && e.getY() < getHeight() - MARGE;

        if (isClickable) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("Roll");
                    push = true;
                    game.roll();
                    displayDice();
                    break;
                case MotionEvent.ACTION_UP:
                    if (push) {
                        System.out.println("Play");
                        //List<Event> events = game.play();
                        //displayEvents(events);
                        push = false;
                    }
                    break;
            }
        } else {
            push = false;
        }

        return true;
    }

    @SuppressLint("NewApi")
    private void displayEvents(Collection<Event> events) {
        events.forEach(this::displayEvent);
    }

    private void displayEvent(Event event) {
        String display = event.play();
        System.out.println(display);
    }

    private void displayDice() {
        for (Dice dice : game.getDices()) {
            System.out.print(dice.getValue() + " ");
        }
        System.out.println();
    }
}
