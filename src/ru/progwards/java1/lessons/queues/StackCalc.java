package ru.progwards.java1.lessons.queues;

import java.util.ArrayDeque;

public class StackCalc {
private ArrayDeque<Double> stack = new ArrayDeque<>();
    public void push(double value) {stack.offerFirst(value);}
    public double pop() {return stack.pollFirst();}
    public void add() {push (pop() + pop());}
    public void sub() {push(-pop() + pop());}
    public void mul() {push(pop() * pop());}
    public void div() {
    // Роботу не понравилось push(1 / pop() * pop());
    double arg1 = pop();
    double arg2 = pop();
    push(arg2 / arg1);
    }
    public double get() {return stack.peekFirst();}
}
