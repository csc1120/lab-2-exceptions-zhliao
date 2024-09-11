/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Main Driver class
 * Name: Zhonghao Liao
 * Last Updated: 09/11/2024
 */
package liao;

import java.util.Scanner;

/**
 * Driver class serves as the entry point for a program that simulates rolling dice.
 * It handles user input to configure the number of dice, the number of sides per die,
 * and the number of rolls to perform. The program then generates the results, calculates
 * the frequency of each possible sum, and displays a formatted report as a horizontal bar chart.
 * @author liao
 */
public class Driver {
    /**
     * The minimum number of dice allowed in the game.
     */
    public static final int MIN_DICE = 2;

    /**
     * The maximum number of dice allowed in the game.
     */
    public static final int MAX_DICE = 10;

    public static void main(String[] args) {
        boolean diceCreated = false;
        int[] userInputs = new int[3];
        Die[] dice = null;
        int[] rolls = null;
        int numDice = 0;
        int numSides = 0;
        int numRolls = 0;
        int max = 0;
        while(!diceCreated) {
            userInputs = getInput();
            numDice = userInputs[0];
            numSides = userInputs[1];
            numRolls = userInputs[2];
            try {
                dice = createDice(numDice, numSides);
                diceCreated = true;
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        rolls = rollDice(dice, numSides, numRolls);
        max = findMax(rolls);
        report(numDice, rolls, max);
    }
    /**
     * This method prompts the user to enter three numbers on a single line separated by spaces.
     * The numbers represent:
     * 1. The number of dice to roll in the experiment
     * 2. The number of sides these dice will have
     * 3. The number of times the dice will be rolled
     *
     * @return An integer array containing the three numbers entered by the user.
     */
    public static int[] getInput() {
        int[] inputs = null;
        boolean validInput = false;
        Scanner read = new Scanner(System.in);
        do {
            try {
                printPrompt();
                String inputLine = read.nextLine();
                String[] parts = inputLine.split(" ");

                if(parts.length != 3) {
                    throw new IllegalArgumentException("Invalid input: Expected 3 values " +
                            "but only received " + parts.length);
                }

                // Convert the input strings to integers
                int numDice = Integer.parseInt(parts[0]); // Number of dice
                int numSides = Integer.parseInt(parts[1]); // Number of sides per die
                int numRolls = Integer.parseInt(parts[2]); // Number of rolls

                // Validate the number of sides
                if (numDice < MIN_DICE || numDice > MAX_DICE) {
                    throw new IllegalArgumentException("Bad die creation: Illegal number of dices: "
                            + numDice);
                }

                // Store valid input in the array
                inputs = new int[] {numDice, numSides, numRolls};
                validInput = true;
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid whole number
                System.out.println("Invalid input: All values must be whole numbers.");
                printPrompt(); // Print the prompt again for clarity

            } catch (IllegalArgumentException e) {
                // Handle invalid input length and out-of-range sides
                System.out.println(e.getMessage());
                printPrompt(); // Print the prompt again for clarity

            }
        } while(!validInput);
        return inputs;
    }

    /**
     * Creates an array of {@code Die} objects with a specified number of dice,
     * each having the specified number of sides.
     *
     * @param numDice the number of dice to create
     * @param numSides the number of sides each die should have;
     *                 must be within the valid range defined by the {@code Die} class.
     * @return an array of {@code Die} objects, all initialized with the specified number of sides.
     * @throws IllegalArgumentException if the number of sides is out of the valid range for dice.
     */
    public static Die[] createDice(int numDice, int numSides) {
        Die[] dice = new Die[numDice];
        for (int i = 0; i< numDice; i++) {
            dice[i] = new Die(numSides);
        }
        return dice;
    }

    /**
     * Simulates rolling a specified number of dice a given number of times
     * and tracks the frequency of each possible sum.
     *
     * @param dice an array of {@code Die} objects to be rolled.
     * @param numSides the number of sides on each die;
     *                 used to determine the maximum possible sum.
     * @param numRolls the number of times to roll the dice.
     * @return an array where each index represents a possible sum of dice rolls,
     *         and the value at that index indicates the frequency of that sum occurring.
     */
    public static int[] rollDice(Die[] dice, int numSides, int numRolls) {
        int minSum = dice.length;
        int maxSum = numSides * dice.length;
        int[] rolls = new int[maxSum - minSum + 1];

        for(int i = 0; i < numRolls; i++) {
            int sum = 0;
            for(Die die : dice) {
                die.roll();
                sum += die.getCurrentValue();
            }
            rolls[sum - minSum]++;
        }
        return rolls;
    }

    /**
     * Finds the maximum value in an array of integers.
     *
     * @param rolls an array of integers representing roll counts.
     * @return the maximum value found in the array.
     */
    public static int findMax(int[] rolls) {
        int max = 0;
        for(int roll : rolls) {
            if(roll > max) {
                max = roll;
            }
        }
        return max;
    }

    /**
     * Generates and prints a report showing the frequency of each roll sum
     * using a bar chart with asterisks.
     *
     * @param numDice the number of dice rolled.
     * @param rolls   an array representing the count of each sum.
     * @param max     the maximum frequency to scale the chart.
     */
    public static void report(int numDice, int[] rolls, int max) {
        final int percentageScale = 10;
        int scale = max / percentageScale;
        for(int i = 0; i < rolls.length; i++) {
            int numStars = rolls[i] / scale;
            // Use StringBuilder to create a line of asterisks
            StringBuilder stars = new StringBuilder();
            for (int j = 0; j < numStars; j++) {
                stars.append("*");
            }
            System.out.printf("%-2d:%-7d %s%n", i + numDice, rolls[i], stars);
        }
    }

    /**
     * Prints the user prompt for entering dice configuration.
     */
    private static void printPrompt() {
        System.out.println("Please enter the number of dice to roll, " +
                "how many sides the dice have,");
        System.out.println("and how many rolls to complete, separating the values by a space.");
        System.out.println("Example: 2 6 1000");
        System.out.println();
        System.out.print("Enter configuration:");
    }
}