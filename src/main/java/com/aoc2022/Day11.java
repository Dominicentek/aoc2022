package com.aoc2022;

import java.util.ArrayList;

public class Day11 extends Day {
    /*public static Monkey[] monkeysDefault = {
        new Monkey((int old) -> old * 5, (int worryLevel) -> worryLevel % 11 == 0, 5, 6, 73, 77),
        new Monkey((int old) -> old + 5, (int worryLevel) -> worryLevel % 19 == 0, 0, 6, 57, 88, 80),
        new Monkey((int old) -> old * 19, (int worryLevel) -> worryLevel % 5 == 0, 1, 3, 61, 81, 84, 69, 77, 88),
        new Monkey((int old) -> old + 7, (int worryLevel) -> worryLevel % 3 == 0, 0, 1, 78, 89, 71, 60, 81, 84, 87, 75),
        new Monkey((int old) -> old + 2, (int worryLevel) -> worryLevel % 13 == 0, 7, 2, 60, 76, 90, 63, 86, 87, 89),
        new Monkey((int old) -> old + 1, (int worryLevel) -> worryLevel % 17 == 0, 7, 4, 88),
        new Monkey((int old) -> old * old, (int worryLevel) -> worryLevel % 7 == 0, 4, 5, 84, 98, 78, 85),
        new Monkey((int old) -> old + 4, (int worryLevel) -> worryLevel % 2 == 0, 2, 3, 98, 89, 78, 73, 71),
    };*/
    public static Monkey[] monkeysDefault = {
        new Monkey((int old) -> old * 19, (int worryLevel) -> worryLevel % 23 == 0, 2, 3, 79, 98),
        new Monkey((int old) -> old + 6, (int worryLevel) -> worryLevel % 19 == 0, 2, 0, 54, 65, 75, 74),
        new Monkey((int old) -> old * old, (int worryLevel) -> worryLevel % 13 == 0, 1, 3, 79, 60, 97),
        new Monkey((int old) -> old + 3, (int worryLevel) -> worryLevel % 17 == 0, 0, 1, 74),
    }; // From example
    public static Monkey[] monkeys = new Monkey[monkeysDefault.length];
    public Day11() throws Exception {
        super("day11.txt");
    }
    public String calculate(boolean secondHalf) throws Exception {
        resetMonkeys();
        for (int i = 0; i < (secondHalf ? 10000 : 20); i++) {
            int round = i + 1;
            boolean display = (round % 1000 == 0 || round == 1 || round == 20) && secondHalf;
            if (display) System.out.println("== After round " + round + " ==");
            for (int j = 0; j < monkeys.length; j++) {
                monkeys[j].inspectItems(secondHalf);
                if (display) System.out.println("Monkey " + j + " inspected items " + monkeys[j].inspectedItems + " times");
            }
            if (display) System.out.println();
        }
        for (int i = 0; i < monkeys.length; i++) {
            for (int j = i + 1; j < monkeys.length; j++) {
                if (monkeys[i].inspectedItems < monkeys[j].inspectedItems) {
                    Monkey temp = monkeys[i];
                    monkeys[i] = monkeys[j];
                    monkeys[j] = temp;
                }
            }
        }
        long monkeyBusiness = (long)monkeys[0].inspectedItems * (long)monkeys[1].inspectedItems;
        return "" + monkeyBusiness;
    }
    public void resetMonkeys() {
        for (int i = 0; i < monkeys.length; i++) {
            monkeys[i] = monkeysDefault[i].copy();
        }
    }
    public static class Monkey {
        public ArrayList<Integer> items = new ArrayList<>();
        public int falseMonkey;
        public int trueMonkey;
        public Operation operation;
        public Decision decision;
        public int inspectedItems = 0;
        public Monkey(Operation operation, Decision decision, int trueMonkey, int falseMonkey, int... startItems) {
            for (int item : startItems) {
                items.add(item);
            }
            this.falseMonkey = falseMonkey;
            this.trueMonkey = trueMonkey;
            this.operation = operation;
            this.decision = decision;
        }
        public void inspectItems(boolean secondHalf) {
            while (!items.isEmpty()) { // WHAT IN THE FUCKING HELL IS WRONG HERE
                inspectedItems++;
                int item = items.get(0);
                items.remove(0);
                int worryLevel = operation.operation(item);
                if (!secondHalf) worryLevel /= 3;
                monkeys[decision.decide(worryLevel) ? trueMonkey : falseMonkey].items.add(worryLevel);
            }
        }
        public Monkey copy() {
            int[] items = new int[this.items.size()];
            for (int i = 0; i < items.length; i++) {
                items[i] = this.items.get(i);
            }
            Monkey monkey = new Monkey(operation, decision, trueMonkey, falseMonkey, items);
            monkey.inspectedItems = inspectedItems;
            return monkey;
        }
    }
    public interface Operation {
        int operation(int old);
    }
    public interface Decision {
        boolean decide(int worryLevel);
    }
}
