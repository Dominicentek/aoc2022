package com.aoc2022;

import java.awt.Point;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Day15 extends Day {
    public Day15() throws Exception {
        super("day15.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        int yLevel = 2000000;
        HashSet<Range> ranges = new HashSet<>();
        LinkedHashMap<Point, Point> sensors = new LinkedHashMap<>();
        String[] lines = input.split("\n");
        for (String line : lines) {
            int sensorX;
            int sensorY;
            int beaconX;
            int beaconY;
            String[] parts = line.split("=");
            for (int i = 1; i <= 4; i++) {
                if (parts[i].indexOf(',') == -1 && parts[i].indexOf(':') == -1) continue;
                if (parts[i].indexOf(',') != -1) parts[i] = parts[i].substring(0, parts[i].indexOf(','));
                if (parts[i].indexOf(':') != -1) parts[i] = parts[i].substring(0, parts[i].indexOf(':'));
            }
            sensorX = Integer.parseInt(parts[1]);
            sensorY = Integer.parseInt(parts[2]);
            beaconX = Integer.parseInt(parts[3]);
            beaconY = Integer.parseInt(parts[4]);
            sensors.put(new Point(sensorX, sensorY), new Point(beaconX, beaconY));
            if (!secondHalf) {
                int steps = Math.abs(sensorX - beaconX) + Math.abs(sensorY - beaconY);
                int distanceY = Math.abs(sensorY - yLevel);
                int length = steps * 2 + 1;
                length -= distanceY * 2;
                if (length < 0) length = 0;
                ranges.add(new Range(sensorX - (length - 1) / 2, sensorX + (length - 1) / 2));
            }
        }
        if (secondHalf) {
            for (Point sensor : sensors.keySet()) {
                Point nearestBeacon = sensors.get(sensor);
                int size = Math.abs(sensor.x - nearestBeacon.x) + Math.abs(sensor.y - nearestBeacon.y);
                int x = 0;
                int y = sensor.y - size - 1;
                boolean decrease = false;
                while (x >= 0) {
                    for (int factor = -1; factor <= 1; factor += 2) {
                        Point p = new Point(sensor.x + x * factor, y);
                        if (p.x < 0 || p.y < 0 || p.x >= 4000000 || p.y >= 4000000) continue;
                        boolean containsInOther = false;
                        for (Point s : sensors.keySet()) {
                            int dist = Math.abs(s.x - sensors.get(s).x) + Math.abs(s.y - sensors.get(s).y);
                            int current = Math.abs(s.x - p.x) + Math.abs(s.y - p.y);
                            if (dist >= current) containsInOther = true;
                        }
                        if (!containsInOther) return "" + ((long)p.x * 4000000 + p.y);
                    }
                    x += decrease ? -1 : 1;
                    y++;
                    if (size == x) decrease = true;
                }
            }
            return "ERROR";
        }
        else {
            int absMin = Integer.MAX_VALUE;
            int absMax = Integer.MIN_VALUE;
            for (Range range : ranges) {
                if (range.min < absMin) absMin = range.min;
                if (range.max > absMax) absMax = range.max;
            }
            int cannotBeAt = 0;
            for (int i = absMin; i <= absMax; i++) {
                if (sensors.containsValue(new Point(i, yLevel))) continue;
                for (Range range : ranges) {
                    if (range.contains(i)) {
                        cannotBeAt++;
                        break;
                    }
                }
            }
            return "" + cannotBeAt;
        }
    }
    public static class Range {
        public int min;
        public int max;
        public Range(int min, int max) {
            this.min = min;
            this.max = max;
            if (min > max) throw new IllegalStateException("Invalid range");
        }
        public boolean contains(int x) {
            return min <= x && x <= max;
        }
        public boolean equals(Object obj) {
            if (obj instanceof Range) {
                Range range = (Range)obj;
                return range.min == min && range.max == max;
            }
            return false;
        }
    }
}
