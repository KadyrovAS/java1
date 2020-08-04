package ru.progwards.java1.lessons.files;

import java.time.LocalDateTime;
import java.util.*;

public class Order {
    public String shopId; //идентификатор магазина
    public String orderId; //идентификатор заказа
    public String customerId; //идентификатор покупателя
    public LocalDateTime datetime; //дата-время заказа (из атрибутов файла - дата последнего изменения)
    public List<OrderItem> items = new LinkedList<OrderItem>();  //список позиций в заказе, отсортированный по наименованию товара
    public double sum; //сумма стоимости всех позиций в заказе
}
