package com.aoc2022;

public class Day8 extends Day {
    public Day8() throws Exception {
        super("day8.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        int[][] trees = new int[input.split("\n")[0].length()][input.split("\n").length];
        String[] lines = input.split("\n");
        for (int x = 0; x < trees.length; x++) {
            for (int y = 0; y < trees[0].length; y++) {
                trees[x][y] = lines[y].charAt(x) - '0';
            }
        }
        int visibleTrees = 0;
        int scenicScore = Integer.MIN_VALUE;
        for (int x = 0; x < trees.length; x++) {
            for (int y = 0; y < trees[0].length; y++) {
                if (processTree(trees, x, y, -1, 0) >= 0 ||
                    processTree(trees, x, y, 0, -1) >= 0 ||
                    processTree(trees, x, y, 1, 0) >= 0 ||
                    processTree(trees, x, y, 0, 1) >= 0) visibleTrees++;
                int score = processTree(trees, x, y, -1, 0) *
                    processTree(trees, x, y, 0, -1) *
                    processTree(trees, x, y, 1, 0) *
                    processTree(trees, x, y, 0, 1);
                if (score < 0) score *= -1;
                if (score > scenicScore) scenicScore = score;
            }
        }
        return "" + (secondHalf ? scenicScore : visibleTrees);
    }
    public int processTree(int[][] trees, int treeX, int treeY, int directionX, int directionY) {
        int tree = trees[treeX][treeY];
        treeX += directionX;
        treeY += directionY;
        int score = 0;
        while (treeX >= 0 && treeY >= 0 && treeX < trees.length && treeY < trees[0].length) {
            score++;
            if (trees[treeX][treeY] >= tree) return -score;
            treeX += directionX;
            treeY += directionY;
        }
        return score;
    }
}
