package com.aoc2022;

public class Day2 extends Day {
    public static final int ROCK = 0;
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;
    public Day2() throws Exception {
        super("day2.txt");
    }
    public int calculate() throws Exception {
        String[] lines = input.split("\n");
        int score = 0;
        for (String line : lines) {
            int opponent = line.charAt(0) - 'A';
            int you = line.charAt(2) - 'X';
            if (opponent == you) score += 3; // Draw
            else {
                opponent++;
                if (opponent == you) score += 6; // Win
             // else Nothing
            }
            score += you + 1; // Choice Score
        }
        return score;
    }
}
