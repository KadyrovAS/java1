package ru.progwards.java1.lessons.files;

public class OrderItem {
    public String googsName; //наименование товара
    public int count; //количество
    public double price; //цена за единицу
    OrderItem() {
    this.googsName = "";
    this.count = 0;
    this.price = 0;
    }

    OrderItem (String googsName, int count, double price) {
        this.googsName = googsName;
        this.count = count;
        this.price = price;
    }
}
