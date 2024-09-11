/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Die class
 * Name: Zhonghao Liao
 * Last Updated: 09/11/2024
 */
package liao;

import java.util.Random;

public class Die {
    public static final int MIN_SIDES = 2;
    public static final int MAX_SIDES = 100;

    private int currentValue;
    private int numSides;
    private Random random;

    public Die(int numSides) {
        if (numSides < MIN_SIDES || numSides > MAX_SIDES) {
            throw new IllegalArgumentException("Bad die creation: Illegal number of sides: " + numSides);
        }
        this.numSides = numSides;
        this.random = new Random();
        this.currentValue = 0; // currentValue is not set in the constructor; initialized to 0
    }

    public int getCurrentValue() {
        if (currentValue != 0) {
            int val = currentValue;
            currentValue = 0;
            return val;
        } else {
            throw new DieNotRolledException("Die not rolled");
        }
    }

    public void roll() {
        currentValue = random.nextInt(numSides) + 1;
    }

}