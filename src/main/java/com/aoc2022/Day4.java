package com.aoc2022;

import java.util.ArrayList;
import java.util.Random;

public class Day4 extends Day {
    public Day4() throws Exception {
        super("day4.txt");
    }
    public int calculate() throws Exception {
        String[] lines = input.split("\n");
        ArrayList<RangePair> pairs = new ArrayList<>();
        for (String line : lines) {
            pairs.add(new RangePair(line));
        }
        int contains = 0;
        int intersects = 0;
        for (RangePair pair : pairs) {
            if (pair.containsOther()) contains++;
            if (pair.intersect()) intersects++;
        }
        return /* (First Half) contains */ intersects;
    }
    public static class Range {
        public int max;
        public int min;
        public Range(String range) {
            String[] numbers = range.split("-");
            min = Integer.parseInt(numbers[0]);
            max = Integer.parseInt(numbers[1]);
        }
        public boolean contains(Range other) {
            return other.min >= min && max >= other.max;
        }
        public boolean intersects(Range other) {
            return min <= other.min ? other.min <= max : min <= other.max;
        }
    }
    public static class RangePair {
        public Range range1;
        public Range range2;
        public RangePair(String pair) {
            range1 = new Range(pair.split(",")[0]);
            range2 = new Range(pair.split(",")[1]);
        }
        public boolean containsOther() {
            return range1.contains(range2) || range2.contains(range1);
        }
        public boolean intersect() {
            return range1.intersects(range2) || range2.intersects(range1);
        }
    }
}
