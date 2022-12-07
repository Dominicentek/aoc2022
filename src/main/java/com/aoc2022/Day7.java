package com.aoc2022;

import java.util.ArrayList;

public class Day7 extends Day {
    public Day7() throws Exception {
        super("day7.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        String[] lines = input.split("\n");
        Directory root = new Directory(null, "");
        Directory current = root;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].substring(2);
            String[] args = line.split(" ");
            if (args[0].equals("cd")) {
                if (args[1].equals("/")) current = root;
                else {
                    if (args[1].startsWith("/")) current = root.subdir(args[1].substring(1));
                    else current = current.subdir(args[1]);
                }
            }
            else if (args[0].equals("ls")) {
                String entry = "";
                while (true) {
                    i++;
                    if (i == lines.length) break;
                    entry = lines[i];
                    if (entry.startsWith("$")) {
                        i--;
                        break;
                    }
                    String arg = entry.split(" ")[0];
                    String name = entry.split(" ")[1];
                    Entry e;
                    if (arg.equals("dir")) e = new Directory(current, name);
                    else e = new File(current, name, Integer.parseInt(arg));
                    current.entries.add(e);
                }
            }
        }
        if (secondHalf) {
            int total = 70000000;
            int unused = total - root.dirsize();
            int updateSize = 30000000;
            return "" + root.findSmallestSizeOfSubdirAboveLimit(updateSize - unused);
        }
        else return "" + root.calcSum(1000000);
    }
    public static abstract class Entry {
        public String name;
        public Directory parent;
        public Entry(Directory parent, String name) {
            this.name = name;
            this.parent = parent;
        }
    }
    public static class Directory extends Entry {
        public ArrayList<Entry> entries = new ArrayList<>();
        private static int smallestSoFar;
        public Directory(Directory parent, String name) {
            super(parent, name);
        }
        public int dirsize() {
            int dirsize = 0;
            for (Entry entry : entries) {
                if (entry instanceof Directory) dirsize += ((Directory)entry).dirsize();
                else dirsize += ((File)entry).size;
            }
            return dirsize;
        }
        public int calcSum(int limit) {
            int sum = 0;
            int size = dirsize();
            if (size <= limit) sum += size;
            for (Entry entry : entries) {
                if (entry instanceof Directory) sum += ((Directory)entry).calcSum(limit);
            }
            return sum;
        }
        public int findSmallestSizeOfSubdirAboveLimit(int size) {
            smallestSoFar = Integer.MAX_VALUE;
            findSmallestSizeOfSubdirAboveLimitInner(size);
            return smallestSoFar;
        }
        private void findSmallestSizeOfSubdirAboveLimitInner(int size) {
            int s = dirsize();
            if (s < smallestSoFar && s >= size) smallestSoFar = s;
            for (Entry entry : entries) {
                if (entry instanceof Directory) ((Directory)entry).findSmallestSizeOfSubdirAboveLimitInner(size);
            }
        }
        public Entry get(String path) {
            if (path.isEmpty() || path.equals(".")) return this;
            if (path.equals("..")) return parent;
            if (path.contains("/")) return subdir(path.split("/")[0]).get(path.substring(path.indexOf("/") + 1));
            for (Entry entry : entries) {
                if (entry.name.equals(path)) return entry;
            }
            return null;
        }
        public Directory subdir(String path) {
            Entry entry = get(path);
            if (entry == null) return null;
            if (entry instanceof File) return null;
            return (Directory)entry;
        }
    }
    public static class File extends Entry {
        public int size;
        public File(Directory parent, String name, int size) {
            super(parent, name);
            this.size = size;
        }
    }
}
