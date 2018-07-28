package com.mgmf.monglaivemonfoie.model;

/**
 * @author Mathieu
 */

public class Dices {
    private Dice dice1;
    private Dice dice2;
    private Dice specialDice;

    public Dices(Dice dice1, Dice dice2, Dice specialDice) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.specialDice = specialDice;
    }

    public Dice getDice1() {
        return dice1;
    }

    public void setDice1(Dice dice1) {
        this.dice1 = dice1;
    }

    public Dice getDice2() {
        return dice2;
    }

    public void setDice2(Dice dice2) {
        this.dice2 = dice2;
    }

    public Dice getSpecialDice() {
        return specialDice;
    }

    public void setSpecialDice(Dice specialDice) {
        this.specialDice = specialDice;
    }

    public Dice getGreater() {
        return dice1.getValue() > dice2.getValue() ? dice1 : dice2;
    }

    public Dice getLower() {
        return dice1.getValue() < dice2.getValue() ? dice1 : dice2;
    }
}
