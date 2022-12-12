package com.aoc2022;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

public class Day12 extends Day {
    public static final boolean VISUALIZE = false; // and im doing it once again
    public static JFrame frame;
    public static BufferedImage img;
    public static Graphics g;
    public ArrayList<Path> paths = new ArrayList<>();
    public ArrayList<Point> taken = new ArrayList<>();
    public ArrayList<Path> takenPath = new ArrayList<>();
    public int secondHalfYStart = 0;
    public Day12() throws Exception {
        super("day12.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        String[] lines = input.split("\n");
        int width = lines[0].length();
        int height = lines.length;
        if (VISUALIZE && !secondHalf) initVisualization(width, height);
        int[][] terrain = new int[width][height];
        int x = 0;
        int y = 0;
        int endX = 0;
        int endY = 0;
        for (int Y = 0; Y < height; Y++) {
            int maxHeight = 0;
            for (int X = 0; X < width; X++) {
                char character = lines[Y].charAt(X);
                int terrainHeight;
                if (character == 'S') {
                    x = X;
                    y = Y;
                    character = 'a';
                }
                if (character == 'E') {
                    endX = X;
                    endY = Y;
                    character = 'z';
                }
                terrainHeight = character - 'a';
                if (maxHeight < terrainHeight) maxHeight = terrainHeight;
                terrain[X][Y] = terrainHeight;
            }
        }
        if (VISUALIZE) visualizeTerrain(terrain);
        int min = 1000;
        for (int i = 0; i < (secondHalf ? height : 1); i++) {
            Path path = new Path();
            Point start = new Point(x, secondHalf ? i : y);
            paths.clear();
            taken.clear();
            takenPath.clear();
            paths.add(path);
            path.path.add(start);
            Path correct;
            if (VISUALIZE) addPoint(start);
            while ((correct = contains(endX, endY)) == null) {
                advance(terrain);
            }
            if (VISUALIZE) {
                visualizeTerrain(terrain);
                for (Point point : correct.path) {
                    addPoint(point);
                }
                Thread.sleep(1000);
            }
            if (!secondHalf) {
                for (Point point : correct.path) {
                    if (point.y > secondHalfYStart) secondHalfYStart = point.y;
                }
            }
            if (min > correct.path.size()) min = correct.path.size();
        }
        return min + "";
    }
    public void advance(int[][] terrain) {
        ArrayList<Path> paths = new ArrayList<>(this.paths);
        for (Path path : paths) {
            path.advance(terrain);
        }
    }
    public Path contains(int x, int y) {
        for (int i = 0; i < taken.size(); i++) {
            Point point = taken.get(i);
            if (point.x == x && point.y == y) return takenPath.get(i);
        }
        return null;
    }
    public class Path {
        public ArrayList<Point> path = new ArrayList<>();
        public void advance(int[][] terrain) {
            paths.remove(this);
            advanceDirection(terrain, -1, 0);
            advanceDirection(terrain, 1, 0);
            advanceDirection(terrain, 0, -1);
            advanceDirection(terrain, 0, 1);
        }
        public void advanceDirection(int[][] terrain, int dirX, int dirY) {
            Point current = path.get(path.size() - 1);
            int x = current.x;
            int y = current.y;
            x += dirX;
            y += dirY;
            if (x < 0 || y < 0 || x >= terrain.length || y >= terrain[0].length) return;
            int height = terrain[current.x][current.y];
            int targetHeight = terrain[x][y];
            Point target = new Point(x, y);
            if (height + 1 >= targetHeight) {
                if (contains(x, y) != null) return;
                Path copy = copy();
                copy.path.add(target);
                paths.add(copy);
                takenPath.add(this);
                taken.add(target);
                if (VISUALIZE) {
                    try {
                        Thread.sleep(3);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    addPoint(target);
                }
            }
        }
        public Path copy() {
            Path copy = new Path();
            copy.path.addAll(path);
            return copy;
        }
    }
    public void initVisualization(int width, int height) {
        img = new BufferedImage(width * 8, height * 8, BufferedImage.TYPE_INT_RGB);
        g = img.getGraphics();
        frame = new JFrame("Visualization");
        frame.setDefaultCloseOperation(3);
        frame.getContentPane().setPreferredSize(new Dimension(width * 8, height * 8));
        frame.pack();
        frame.add(new JPanel() {
            public void paint(Graphics g) {
                g.drawImage(img, 0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight(), this);
            }
        });
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public void visualizeTerrain(int[][] terrain) {
        for (int x = 0; x < terrain.length; x++) {
            for (int y = 0; y < terrain[x].length; y++) {
                int gray = (int)((terrain[x][y] / 26f * (255 - 32)) + 32);
                g.setColor(new Color(gray, gray, gray));
                g.fillRect(x * 8, y * 8, 8, 8);
            }
        }
        frame.repaint();
    }
    public void addPoint(Point point) {
        g.setColor(new Color(0, 0, 255, 127));
        g.fillRect(point.x * 8, point.y * 8, 8, 8);
        frame.repaint();
    }
}
