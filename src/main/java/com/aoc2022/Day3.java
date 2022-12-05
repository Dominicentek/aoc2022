package com.aoc2022;

public class Day3 extends Day {
    public Day3() throws Exception {
        super("day3.txt");
    }
    public String calculate() throws Exception {
        String[] rucksacks = input.split("\n");
        /*
        First Half

        char[] characters = new char[rucksacks.length];
        for (int i = 0; i < rucksacks.length; i++) {
            int items = rucksacks[i].length();
            int compartmentLength = items / 2;
            String compartment1 = rucksacks[i].substring(0, compartmentLength);
            String compartment2 = rucksacks[i].substring(compartmentLength);
            for (char character : compartment1.toCharArray()) {
                if (compartment2.indexOf(character) != -1) {
                    characters[i] = character;
                    break;
                }
            }
        }
        */
        // Second Half
        char[] characters = new char[rucksacks.length / 3];
        for (int i = 0; i < rucksacks.length / 3; i++) {
            String rucksack = rucksacks[i * 3];
            for (char character : rucksack.toCharArray()) {
                if (rucksacks[i * 3 + 1].indexOf(character) != -1 && rucksacks[i * 3 + 2].indexOf(character) != -1) {
                    characters[i] = character;
                    break;
                }
            }
        }
        //////////
        int sum = 0;
        for (char character : characters) {
            int x = 0;
            if (character >= 'a' && character <= 'z') x = character - 'a';
            if (character >= 'A' && character <= 'Z') x = character - 'A' + 26;
            x++;
            sum += x;
        }
        return "" + sum;
    }
}
