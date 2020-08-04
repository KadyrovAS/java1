package ru.progwards.java1.lessons.files;

public class OrderItem {
    public String googsName; //наименование товара
    public int count; //количество
    public double price; //цена за единицу
    public OrderItem (String googsName, int count, double price) {
        System.out.println("OrderItem() googsName = '" + googsName+ "'; " +
                "count = " + count + " price = " + price);
        this.googsName = googsName;
        this.count = count;
        this.price = price;
    }
}
