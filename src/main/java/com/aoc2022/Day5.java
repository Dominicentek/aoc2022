package com.aoc2022;

import java.util.HashMap;
import java.util.Stack;

public class Day5 extends Day {
    public HashMap<Character, Stack<Character>> crateStacks = new HashMap<>();
    public Day5() throws Exception {
        super("day5.txt");
    }
    public String calculate() throws Exception {
        String[] lines = input.split("\n");
        int index = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isEmpty()) {
                index = i;
                break;
            }
        }
        index--;
        HashMap<Integer, Character> columns = new HashMap<>();
        String crateLabelRow = lines[index];
        char[] labels = crateLabelRow.toCharArray();
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] > 32 && labels[i] <= 126) {
                columns.put(i, labels[i]);
                crateStacks.put(labels[i], new Stack<>());
            }
        }
        index--;
        for (int i = index; i >= 0; i--) {
            String line = lines[i];
            for (int column : columns.keySet()) {
                char label = columns.get(column);
                char character = line.charAt(column);
                Stack<Character> stack = crateStacks.get(label);
                if (!stack.isEmpty() && stack.peek() == null) continue;
                if (character > 32 && character <= 126) stack.push(character);
                else stack.push(null);
            }
        }
        for (char label : crateStacks.keySet()) {
            Stack<Character> stack = crateStacks.get(label);
            while (stack.peek() == null) stack.pop();
        }
        index += 3;
        for (int i = index; i < lines.length; i++) {
            String line = lines[i].replaceAll("move ", "").replaceAll(" from", "").replaceAll(" to", "");
            String[] args = line.split(" ");
            int amount = Integer.parseInt(args[0]);
            char from = args[1].charAt(0);
            char to = args[2].charAt(0);
            Stack<Character> src = crateStacks.get(from);
            Stack<Character> dst = crateStacks.get(to);
            /*
            First Half

            for (int j = 0; j < amount; j++) {
                dst.push(src.pop());
            }
            */
            // Second Half
            Stack<Character> grabbed = new Stack<>();
            for (int j = 0; j < amount; j++) {
                grabbed.push(src.pop());
            }
            for (int j = 0; j < amount; j++) {
                dst.push(grabbed.pop());
            }
            //////////
        }
        String topItems = "";
        for (char label : crateStacks.keySet()) {
            topItems += "" + crateStacks.get(label).peek();
        }
        return topItems;
    }
}