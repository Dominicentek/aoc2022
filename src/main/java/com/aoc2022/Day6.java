package com.aoc2022;

public class Day6 extends Day {
    public static final int MARKER_LENGTH = /* First Half   4 */ 14;
    public Day6() throws Exception {
        super("day6.txt");
    }
    public String calculate() throws Exception {
        for (int i = 0; i < input.length() - MARKER_LENGTH; i++) {
            boolean allDifferent = true;
            for (int j = 0; j < MARKER_LENGTH; j++) {
                for (int k = j - 1; k >= 0; k--) {
                    if (input.charAt(i + k) == input.charAt(i + j)) allDifferent = false;
                }
            }
            if (allDifferent) return "" + (i + MARKER_LENGTH);
        }
        throw new Exception("wtffffff");
    }
}
