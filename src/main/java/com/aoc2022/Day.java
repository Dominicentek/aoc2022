package com.aoc2022;

import java.io.InputStream;

public abstract class Day {
    public String input;
    public Day(String filename) throws Exception {
        InputStream in = Day.class.getResourceAsStream("/" + filename);
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();
        input = new String(data);
    }
    public final String calculate() throws Exception {
        return "(first: " + calculate(false) + ", second: " + calculate(true) + ")";
    }
    public abstract String calculate(boolean secondHalf) throws Exception;
}
