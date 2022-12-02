package com.aoc2022;

import java.util.ArrayList;
import java.util.Collections;

public class Day1 extends Day {
    public Day1() throws Exception {
        super("day1.txt");
    }
    public int calculate() throws Exception {
        String[] elves = input.split("\n\n");
        int[] calories = new int[elves.length];
        for (int i = 0; i < elves.length; i++) {
            String elf = elves[i];
            if (elf.isEmpty()) continue;
            String[] lines = elf.split("\n");
            for (String line : lines) {
                int x = Integer.parseInt(line);
                calories[i] += x;
            }
        }
        /*
        First Half

        int max = Integer.MIN_VALUE;
        for (int calorie : calories) {
            if (max < calorie) max = calorie;
        }
        return max;
        */

        // Second Half
        ArrayList<Integer> list = new ArrayList<>();
        for (int calorie : calories) {
            list.add(calorie);
        }
        Collections.sort(list);
        Collections.reverse(list);
        return list.get(0) + list.get(1) + list.get(2);
    }
}
