package com.mgmf.monglaivemonfoie.model;

import java.util.Random;

/**
 * Model class for a dice
 *
 * @author Mathieu Aimé
 */

public class Dice {
    private final boolean special;
    private int value;

    public Dice() {
        this(false);
    }

    public Dice(boolean special) {
        this.special = special;
        this.value = 0;
    }

    public void roll(int faces) {
        Random r = new Random();
        value = r.nextInt(faces) + 1;
    }

    public int getValue() {
        return value;
    }

    public boolean isSpecial() {
        return special;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dice dice = (Dice) o;

        if (special != dice.special) return false;
        return value == dice.value;
    }

    @Override
    public int hashCode() {
        int result = (special ? 1 : 0);
        result = 31 * result + value;
        return result;
    }

    @Override
    public String toString() {
        return value + " ";
    }
}
