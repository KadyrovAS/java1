package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.*;
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
        String[] items;
        String googsName;
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
            System.out.println("start=" + start + "; datetime=" + order.datetime +
                    "; localDate=" + order.datetime.toLocalDate() +
                    "; compare=" + start.compareTo(order.datetime.toLocalDate()));
            System.out.println("finish=" + finish + "; datetime=" + order.datetime +
                    "; localDate=" + order.datetime.toLocalDate() +
                    "; compare=" + finish.compareTo(order.datetime.toLocalDate()));

            if (start != null && start.compareTo(order.datetime.toLocalDate()) > 0) return;
            if (finish != null && finish.compareTo(order.datetime.toLocalDate()) < 0) return;
            for (String line: Files.readAllLines(path)) {
                items = line.split(",");
                if (items.length != 3) continue;
                googsName = items[0];
                count = Integer.valueOf(items[1].trim());
                price = Double.valueOf(items[2].trim());
//                System.out.println("readOrder() googsName = '" + googsName+ "'; " +
//                        "count = " + count + " price = " + price);
                order.items.add(new OrderItem(googsName, count, price));
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
                if (calculateByGoods.get(orderItem.googsName) == null)
                    calculateByGoods.put(orderItem.googsName, orderItem.price * orderItem.count);
                else calculateByGoods.put(orderItem.googsName, orderItem.price * orderItem.count +
                        calculateByGoods.get(orderItem.googsName));
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

    public static void main(String[] args) throws IOException {
//        Path path = Paths.get("d:/orders/01.txt");
//        Path filePath;
//        String[] attr;
//        String dateStr;
//        String fileName;
//        LocalDateTime dateTime;
//        FileTime fileTime;
//        for (String fileLines: Files.readAllLines(path)) {
//            attr = fileLines.split(",");
//            fileName = attr[0].substring(2);
//            dateStr = attr[1].substring(7);
//            dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

//            filePath = Paths.get("d:/orders/" + fileName);
//            System.out.println(filePath);
//            fileTime = Files.getLastModifiedTime(filePath);
//            dateTime = fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//            System.out.println(fileName + "  " + fileTime + "  " + dateTime);

//            System.out.println("----------------");
//
        }
//        OrderProcessor orderProcessor = new OrderProcessor("d:/Orders");
//        orderProcessor.loadOrders(null, null, null);
//        for (Order order: orderProcessor.process(null)) {
//            for (OrderItem orderItem : order.items)
//                System.out.println(orderItem.googsName + " " + orderItem.count + " " + orderItem.price);
//        }
//    }

}