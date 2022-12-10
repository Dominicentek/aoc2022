package com.aoc2022;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Day10 extends Day {
    public static final boolean VISUALIZE = true; // yeah, i visualize again
    public static JFrame frame;
    public static BufferedImage img;
    public static Graphics g;
    public static boolean[] pixels = new boolean[40 * 6];
    public Day10() throws Exception {
        super("day10.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        if (VISUALIZE && secondHalf) initVisualization();
        String[] lines = input.split("\n");
        int cycle = 0;
        int x = 1;
        int signalStrengthSum = 0;
        for (String line : lines) {
            int cycles = 0;
            Integer addx = null;
            if (line.startsWith("addx")) {
                cycles = 2;
                addx = Integer.parseInt(line.split(" ")[1]);
            }
            if (line.startsWith("noop")) cycles = 1;
            for (int i = 0; i < cycles; i++) {
                cycle++;
                if (!secondHalf) {
                    if ((cycle + 20) % 40 == 0) {
                        signalStrengthSum += cycle * x;
                    }
                }
                if (i + 1 == cycles && addx != null) x += addx;
                if (secondHalf) {
                    int xPos = cycle % 40;
                    if (cycle < pixels.length) pixels[cycle] = xPos >= x - 1 && xPos <= x + 1;
                    if (VISUALIZE && cycle < pixels.length) {
                        Thread.sleep(100);
                        visualize(pixels, x, cycle);
                    }
                }
            }
        }
        if (secondHalf) {
            for (int i = 0; i < pixels.length; i++) {
                if (i % 40 == 0) System.out.println();
                if (pixels[i]) System.out.print("#");
                else System.out.print(" ");
            }
            System.out.println("\n");
        }
        return secondHalf ? "Check the output above" : "" + signalStrengthSum;
    }
    public void initVisualization() {
        img = new BufferedImage(40 * 16, 6 * 16, BufferedImage.TYPE_INT_RGB);
        g = img.getGraphics();
        frame = new JFrame("Visualization");
        frame.setDefaultCloseOperation(3);
        frame.getContentPane().setPreferredSize(new Dimension(40 * 16, 6 * 16));
        frame.pack();
        frame.add(new JPanel() {
            public void paint(Graphics g) {
                g.drawImage(img, 0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight(), this);
            }
        });
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public void visualize(boolean[] pixels, int x, int cycle) {
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i]) g.setColor(Color.WHITE);
            else g.setColor(Color.BLACK);
            g.fillRect((i % 40) * 16, (i / 40) * 16, 16, 16);
        }
        g.setColor(new Color(0, 0, 255, 127));
        g.fillRect((x - 1) * 16, 0, 3 * 16, 6 * 16);
        g.setColor(Color.RED);
        g.fillRect((cycle % 40) * 16, (cycle / 40) * 16, 16, 16);
        frame.repaint();
    }
}
