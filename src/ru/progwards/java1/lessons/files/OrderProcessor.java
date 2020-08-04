package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class OrderProcessor {
    private Path startPath;
    private int errorCount = 0;
    private List<Order> listOrder;
    public OrderProcessor(String startPath){
        this.startPath = Paths.get(startPath);
        listOrder = new LinkedList<Order>();
    }
    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {
        errorCount = 0;
        String findMask = "glob:**/???-??????-????.csv";
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(findMask);
        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(path)) readOrder(path, start, finish, shopId);
                    else errorCount++;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {errorCount++;}
    return errorCount;
    }

    private void readOrder(Path path, LocalDate start, LocalDate finish, String shopId) {
        Order order = new Order();
        String[] orderItem;
        String goodsName;
        int count;
        double price;
        String fileName = path.getFileName().toString();
        order.shopId = fileName.substring(0, 3);
        if (shopId != null && shopId.compareTo(order.shopId) != 0) return;
        order.orderId = fileName.substring(4, 10);
        order.customerId = fileName.substring(11, 15);
        try {
            order.datetime = Files.getLastModifiedTime(path)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            if (start != null && start.compareTo(order.datetime.toLocalDate()) > 0) return;
            if (finish != null && finish.compareTo(order.datetime.toLocalDate()) < 0) return;
            for (String line: Files.readAllLines(path)) {
                orderItem = line.split(",");
                if (orderItem.length != 3) continue;
                goodsName = orderItem[0];
                count = Integer.valueOf(orderItem[1].trim());
                price = Double.valueOf(orderItem[2].trim());
                order.items.add(new OrderItem(goodsName, count, price));
                order.sum += count * price;
            }
            listOrder.add(order);

        } catch (IOException e) {return;}

    }
    public List<Order> process(String shopId) {
        List<Order> listShopId = new LinkedList<>();
        for (Order order: listOrder) {
            if (shopId == null || shopId.compareTo(order.shopId) == 0)
                listShopId.add(order);
        listShopId.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.datetime.compareTo(o2.datetime);
            }
        });
        }
        return listShopId;
    }
    public Map<String, Double> statisticsByShop(){
        Map<String, Double> calculateByShop = new TreeMap<>();
        for (Order order: listOrder) {
            if (calculateByShop.get(order.shopId) == null)
                calculateByShop.put(order.shopId, order.sum);
            else calculateByShop.put(order.shopId, order.sum +
                    calculateByShop.get(order.shopId));
        }
        return calculateByShop;
    }
    public Map<String, Double> statisticsByGoods(){
        Map<String, Double> calculateByGoods = new TreeMap<>();
        for (Order order: listOrder) {
            for (OrderItem orderItem : order.items) {
                if (calculateByGoods.get(orderItem.goodsName) == null)
                    calculateByGoods.put(orderItem.goodsName, orderItem.price * orderItem.count);
                else calculateByGoods.put(orderItem.goodsName, orderItem.price * orderItem.count +
                        calculateByGoods.get(orderItem.goodsName));
            }
        }
        return calculateByGoods;
    }
    public Map<LocalDate, Double> statisticsByDay(){
        Map<LocalDate, Double> calculateByDate = new TreeMap<>();
        for (Order order: listOrder) {
            if (calculateByDate.get(order.datetime.toLocalDate()) == null)
                calculateByDate.put(order.datetime.toLocalDate(), order.sum);
            else calculateByDate.put(order.datetime.toLocalDate(), order.sum +
                    calculateByDate.get(order.datetime.toLocalDate()));
        }
        return calculateByDate;
    }

    public static void main(String[] args) {
        OrderProcessor orderProcessor = new OrderProcessor("d:/Orders");
        orderProcessor.loadOrders(null, null, null);
        for (Order order: orderProcessor.process(null)) {
            for (OrderItem orderItem : order.items)
                System.out.println(orderItem.goodsName + " " + orderItem.count + " " + orderItem.price);
        }
    }

}
