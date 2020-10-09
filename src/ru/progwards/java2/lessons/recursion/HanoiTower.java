package ru.progwards.java2.lessons.recursion;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;

public class HanoiTower {
    boolean traceOn;
    int size;
    int pos;
    List<LinkedList<Integer>> arTower = new ArrayList<>();

    public HanoiTower(int size, int pos) {
        this.size = size;
        this.pos = pos;
        this.traceOn = false;
        for (int i = 0; i < 3; i++)
            arTower.add(new LinkedList<Integer>());
        LinkedList<Integer> tower = new LinkedList<>();
        for (int i = size; i > 0; i--)
            tower.add(i);
        arTower.set(pos, tower);
    }

    public void move(int from, int to) {
        if (this.traceOn) print();
        moveTo(from, to, this.size);
    }

    public void moveTo(int from, int to, int countElem) {
        if (countElem == 0) return;
        int dif = difNum(from, to);
        if (countElem > 1)
            moveTo(from, dif, countElem - 1);

        int num = arTower.get(from).pollLast();
        LinkedList<Integer> tower = arTower.get(to);
        tower.add(num);
        arTower.set(to, tower);
        if (this.traceOn) print();
        if (num > 1) {
            moveTo(dif,to,num - 1);
        }
    }

    public int difNum(int from, int to) {
        switch (from + to) {
            case (1):
                return 2;
            case (2):
                return 1;
        }
        return 0;
    }

    void print() {
        //выводит текущее состояние башни на консоль
        Formatter fmt;
        String line, str;
        for (int i = 0; i < this.size; i++) {
            line = "";
            for (int k = 0; k < 3; k++) {
                if (arTower.get(k).size() < this.size - i)
                    line += "  I   ";
                else {
                    fmt = new Formatter();
                    str = fmt.format("<%03d> ", arTower.get(k).get(this.size - i - 1)).toString();
                    fmt.close();
                    line += str;
                }
            }
            System.out.println(line);
        }
        System.out.println("=================");
    }

    void setTrace(boolean on) {
        //включает отладочную печать состояния игрового поля после каждого шага алгоритма (метода move)
        this.traceOn = on;
    }

    public static void main(String[] args) {
        HanoiTower tow = new HanoiTower(10, 0);
        tow.setTrace(true);
        tow.move(0, 2);
//        tow.print();
    }
}