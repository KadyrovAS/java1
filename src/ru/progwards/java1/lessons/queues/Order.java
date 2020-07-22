package ru.progwards.java1.lessons.queues;

public class Order implements Comparable<Order>{
    private static int count = 0;
    private double sum; // сумма заказа
    private int num; // номер заказа
    public Order(double sum) {
        this.sum = sum;
        this.num = ++count;
    }

    @Override
    public int compareTo(Order o) {
        if (priorOrder(this) == priorOrder(o))
            return Integer.compare(getNum(), o.getNum());
        return Integer.compare(priorOrder(this), priorOrder(o));
    }

    public double getSum() {return this.sum;}
    public int getNum() {return this.num;}

    private int priorOrder(Order order) {
        if (order.getSum() <= 10_000) return  3;
        else if (order.getSum() <= 20_000) return  2;
        else return 1;
    }

    public static void main(String[] args) {
        Order order = new Order(20);
        System.out.println(order.getNum() + " " + order.getSum());
        order = new Order(35);
        System.out.println(order.getNum() + " " + order.getSum());
    }

}
