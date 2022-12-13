package com.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;

public class Day13 extends Day {
    public Day13() throws Exception {
        super("day13.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        String[] pairsData = input.split("\n\n");
        ArrayList<Element> elements = new ArrayList<>();
        for (int i = 0; i < pairsData.length; i++) {
            String pairData = pairsData[i];
            Element left = parse(pairData.split("\n")[0]);
            Element right = parse(pairData.split("\n")[1]);
            left.index = i * 2 + 1;
            right.index = i * 2 + 2;
            elements.add(left);
            elements.add(right);
        }
        if (secondHalf) {
            Element divider1 = parse("[[2]]");
            Element divider2 = parse("[[6]]");
            divider1.index = elements.size() + 1;
            divider2.index = elements.size() + 2;
            elements.add(divider1);
            elements.add(divider2);
            for (int i = 0; i < elements.size() - 1; i++) {
                while (compare(elements.get(i).copy(), elements.get(i + 1).copy()) == -1) {
                    Element temp = elements.get(i);
                    elements.set(i, elements.get(i + 1));
                    elements.set(i + 1, temp);
                    elements.get(i).index = i + 1;
                    elements.get(i + 1).index = i + 2;
                    if (i > 0) i--;
                }
            }
            return "" + (divider1.index * divider2.index);
        }
        else {
            int sum = 0;
            for (int i = 0; i < elements.size(); i += 2) {
                int comparsion = compare(elements.get(i), elements.get(i + 1));
                if (comparsion == 1) sum += ((i / 2) + 1);
            }
            return "" + sum;
        }
    }
    public Element parse(String data) {
        Element parsed;
        try {
            parsed = new Number(Integer.parseInt(data));
        }
        catch (NumberFormatException e) {
            String element = "";
            ArrayList<String> elements = new ArrayList<>();
            int layer = 0;
            for (int i = 1; i < data.length() - 1; i++) {
                char character = data.charAt(i);
                if (character == '[') layer++;
                if (character == ']') layer--;
                if (character == ',' && layer == 0) {
                    elements.add(element);
                    element = "";
                }
                else element += character;
            }
            if (!element.isEmpty()) elements.add(element);
            List list = new List();
            for (String el : elements) {
                list.items.add(parse(el));
            }
            parsed = list;
        }
        return parsed;
    }
    public int compare(Element left, Element right) {
        if (left instanceof Number && right instanceof List) return compare(new List(left), right);
        if (left instanceof List && right instanceof Number) return compare(left, new List(right));
        if (left instanceof Number && right instanceof Number) {
            int leftNum = ((Number)left).number;
            int rightNum = ((Number)right).number;
            if (leftNum < rightNum) return 1;
            if (leftNum > rightNum) return -1;
            return 0;
        }
        if (left instanceof List && right instanceof List) {
            ArrayList<Element> leftList = ((List)left).items;
            ArrayList<Element> rightList = ((List)right).items;
            while (!leftList.isEmpty() && !rightList.isEmpty()) {
                Element leftElement = leftList.get(0);
                Element rightElement = rightList.get(0);
                leftList.remove(0);
                rightList.remove(0);
                int comparsion = compare(leftElement, rightElement);
                if (comparsion != 0) return comparsion;
            }
            if (leftList.isEmpty() && !rightList.isEmpty()) return 1;
            if (!leftList.isEmpty() && rightList.isEmpty()) return -1;
            return 0;
        }
        throw new IllegalStateException("something went really wrong");
    }
    public abstract static class Element {
        public int index;
        public abstract Element copy();
    }
    public static class List extends Element {
        public ArrayList<Element> items = new ArrayList<>();
        public List(Element... items) {
            this.items.addAll(Arrays.asList(items));
        }
        public String toString() {
            StringBuilder builder = new StringBuilder("[");
            for (Element element : items) {
                builder.append(element).append(",");
            }
            if (items.size() != 0) builder.deleteCharAt(builder.length() - 1);
            builder.append("]");
            return builder.toString();
        }
        public Element copy() {
            List list = new List();
            for (Element element : items) {
                list.items.add(element.copy());
            }
            return list;
        }
    }
    public static class Number extends Element {
        public int number;
        public Number(int number) {
            this.number = number;
        }
        public String toString() {
            return number + "";
        }
        public Element copy() {
            return new Number(number);
        }
    }
}
