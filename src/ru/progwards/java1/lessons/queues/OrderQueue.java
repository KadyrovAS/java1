package ru.progwards.java1.lessons.queues;

import java.util.PriorityQueue;

public class OrderQueue {
    private PriorityQueue<Order> queue = new PriorityQueue<Order>();
    public void add(Order order) {queue.offer(order);}
    public Order get() {return queue.poll();}

    public static void main(String[] args) {
        OrderQueue myQueue = new OrderQueue();
        myQueue.add(new Order(15_000));
        myQueue.add(new Order(20_000));
        myQueue.add(new Order(25_000));
        myQueue.add(new Order(26_000));
        Order order = myQueue.get();
        System.out.println(order.getNum() + " " + order.getSum());

        order = myQueue.get();
        System.out.println(order.getNum() + " " + order.getSum());

        order = myQueue.get();
        System.out.println(order.getNum() + " " + order.getSum());

        order = myQueue.get();
        System.out.println(order.getNum() + " " + order.getSum());
    }

}
