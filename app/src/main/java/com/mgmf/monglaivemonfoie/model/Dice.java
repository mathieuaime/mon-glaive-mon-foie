package com.mgmf.monglaivemonfoie.model;

import java.util.Random;

/**
 * Created by Mathieu on 06/12/2017.
 */

public class Dice {
    public static final int FACES = 6;
    private final boolean special;
    private int value;

    public Dice(boolean special) {
        this.special = special;
        this.value = 0;
    }

    public void roll() {
        Random r = new Random();
        value = r.nextInt(FACES) + 1;
    }

    public int getValue() {
        return value;
    }

    public boolean isSpecial() {
        return special;
    }
}
