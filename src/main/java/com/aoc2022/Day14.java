package com.aoc2022;

public class Day14 extends Day {
    public Day14() throws Exception {
        super("day14.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        boolean[][] cave = new boolean[1000][200];
        String[] lines = input.split("\n");
        int highestY = 0;
        for (String line : lines) {
            String[] parts = line.split(" -> ");
            int currentX = Integer.parseInt(parts[0].split(",")[0]);
            int currentY = Integer.parseInt(parts[0].split(",")[1]);
            if (highestY < currentY) highestY = currentY;
            for (int i = 1; i < parts.length; i++) {
                int x = Integer.parseInt(parts[i].split(",")[0]);
                int y = Integer.parseInt(parts[i].split(",")[1]);
                for (int X = Math.min(currentX, x); X <= Math.max(currentX, x); X++) {
                    for (int Y = Math.min(currentY, y); Y <= Math.max(currentY, y); Y++) {
                        cave[X][Y] = true;
                    }
                }
                currentX = x;
                currentY = y;
                if (highestY < currentY) highestY = currentY;
            }
            cave[currentX][currentY] = true;
        }
        if (secondHalf) {
            highestY += 2;
            for (int i = 0; i < 1000; i++) {
                cave[i][highestY] = true;
            }
        }
        int i = 0;
        while (fall(500, 0, cave)) i++;
        return i + "";
    }
    public boolean fall(int x, int y, boolean[][] cave) {
        if (cave[x][y]) return false;
        while (true) {
            y++;
            if (y == 200) return false;
            if (cave[x][y]) {
                x--;
                if (cave[x][y]) {
                    x += 2;
                    if (cave[x][y]) {
                        y--;
                        x--;
                        cave[x][y] = true;
                        return true;
                    }
                }
            }
        }
    }
}
