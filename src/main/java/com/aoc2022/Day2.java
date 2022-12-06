package com.aoc2022;

public class Day2 extends Day {
    public Day2() throws Exception {
        super("day2.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        String[] lines = input.split("\n");
        int score = 0;
        for (String line : lines) {
            int opponent = line.charAt(0) - 'A';
            int you;
            if (secondHalf) {
                int state = line.charAt(2) - 'X' - 1;
                you = getShape(opponent, state);
            }
            else you = line.charAt(2) - 'X';
            score += rps(you, opponent) * 3 + 3;
            score += you + 1;
        }
        return "" + score;
    }
    public int getShape(int opponent, int state) {
        opponent += state;
        if (opponent < 0) opponent += 3;
        if (opponent >= 3) opponent -= 3;
        return opponent;
    }
    public int rps(int you, int opponent) {
        if (you == opponent) return 0;
        if (you == 0 && opponent == 2) return 1;
        opponent++;
        if (you == opponent) return 1;
        else return -1;
    }
}
