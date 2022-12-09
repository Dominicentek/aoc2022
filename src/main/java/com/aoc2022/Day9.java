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

public class Day9 extends Day {
    public JFrame frame;
    public Graphics gSnake;
    public BufferedImage imgPath;
    public BufferedImage imgSnake;
    public boolean init = false;
    public static final boolean VISUALIZE = false; // i made this for debugging purposes but it was so cool that i decided to keep it
    public Day9() throws Exception {
        super("day9.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        if (VISUALIZE) initVisualization();
        String[] lines = input.split("\n");
        ArrayList<Point> visitedSpots = new ArrayList<>();
        ArrayList<Point> body = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            body.add(new Point(220, 299));
        }
        int maxSteps = 0;
        for (String line : lines) {
            maxSteps += Integer.parseInt(line.substring(2));;
        }
        int step = 0;
        for (String line : lines) {
            int steps = Integer.parseInt(line.substring(2));
            char instruction = line.charAt(0);
            for (int i = 0; i < steps; i++) {
                step++;
                Point head = body.get(0);
                if (instruction == 'U') head.y--;
                if (instruction == 'L') head.x--;
                if (instruction == 'D') head.y++;
                if (instruction == 'R') head.x++;
                for (int j = 1; j < body.size(); j++) {
                    Point previous = body.get(j - 1);
                    Point current = body.get(j);
                    int distanceX = current.x - previous.x;
                    int distanceY = current.y - previous.y;
                    if (distanceX < -1 || distanceY < -1 || distanceX > 1 || distanceY > 1) {
                        current.x = previous.x;
                        current.y = previous.y;
                        if (distanceX < -1) current.x--;
                        if (distanceY < -1) current.y--;
                        if (distanceX > 1) current.x++;
                        if (distanceY > 1) current.y++;
                    }
                }
                Point tail = body.get(body.size() - 1).getLocation();
                if (!visitedSpots.contains(tail)) {
                    if (VISUALIZE) imgPath.setRGB(tail.x, tail.y, 0xFF0000FF);
                    visitedSpots.add(tail);
                }
                if (VISUALIZE) {
                    Thread.sleep(10);
                    visualize(body, step, maxSteps, instruction, steps - i);
                }
            }
        }
        Thread.sleep(5000);
        return "" + visitedSpots.size();
    }
    public void initVisualization() {
        if (!init) frame = new JFrame("Visualization");
        imgPath = new BufferedImage(266, 363, BufferedImage.TYPE_INT_RGB);
        imgSnake = new BufferedImage(266, 363, BufferedImage.TYPE_INT_ARGB);
        gSnake = imgSnake.getGraphics();
        gSnake.setColor(Color.WHITE);
        if (!init) {
            frame.setDefaultCloseOperation(3);
            frame.getContentPane().setPreferredSize(new Dimension(imgPath.getWidth() * 2, imgPath.getHeight() * 2));
            frame.pack();
            frame.add(new JPanel() {
                public void paint(Graphics g) {
                    g.drawImage(imgPath, 0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight(), this);
                    g.drawImage(imgSnake, 0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight(), this);
                }
            });
            frame.setResizable(false);
            frame.setVisible(true);
            new Scanner(System.in).nextLine();
        }
        init = true;
    }
    public void visualize(ArrayList<Point> body, int step, int max, char currentCommand, int stepsRemaining) {
        for (int x = 0; x < imgSnake.getWidth(); x++) {
            for (int y = 0; y < imgSnake.getHeight(); y++) {
                imgSnake.setRGB(x, y, 0x00000000);
            }
        }
        for (int x = 0; x < 266; x++) {
            for (int y = 0; y < 363; y++) {
                Point point = new Point(x, y);
                for (int i = 0; i < body.size(); i++) {
                    if (body.get(i).equals(point)) {
                        int gray = (int)((body.size() * 2 - i) / (body.size() * 2f) * 255);
                        imgSnake.setRGB(x, y, 0xFF000000 | (gray << 16) | (gray << 8) | gray);
                        break;
                    }
                }
            }
        }
        gSnake.drawString("Length: " + body.size(), 5, imgSnake.getHeight() - 35);
        gSnake.drawString(currentCommand + " " + stepsRemaining, 5, imgSnake.getHeight() - 20);
        gSnake.drawString("Step " + step + "/" + max, 5, imgSnake.getHeight() - 5);
        gSnake.drawString("100 steps per sec", 35, imgSnake.getHeight() - 20);
        frame.repaint();
    }
}
