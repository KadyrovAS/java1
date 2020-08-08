package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.*;
import java.util.*;

public class OrderProcessor {
    private Path startPath;
    private int errorCount = 0;
    private List<Order> listOrder;

    public OrderProcessor(String startPath) {
        this.startPath = Paths.get(startPath);
        listOrder = new LinkedList<Order>();
        this.errorCount = 0;
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {
        errorCount = 0;
        String findMask = "glob:**/???-??????-????.csv";
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(findMask);
        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    try {
                        LocalDateTime datetime = Files.getLastModifiedTime(path)
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();

                        if (start != null && start.compareTo(datetime.toLocalDate()) > 0)
                            return FileVisitResult.CONTINUE;
                        if (finish != null && finish.compareTo(datetime.toLocalDate()) < 0)
                            return FileVisitResult.CONTINUE;
                    } catch (IOException e) {
                        return FileVisitResult.CONTINUE;
                    }
                    if (pathMatcher.matches(path)) readOrder(path, shopId);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
        }

        return errorCount;
    }

    private void readOrder(Path path, String shopId) {
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
            for (String line : Files.readAllLines(path)) {
                items = line.split(",");
                if (items.length != 3) {
                    errorCount ++;
                    return;
                }
                googsName = items[0];
                count = Integer.valueOf(items[1].trim());
                price = Double.valueOf(items[2].trim());
                order.items.add(new OrderItem(googsName, count, price));
                order.sum += count * price;
            }
            if (order.sum > 0) {
                order.items.sort(new Comparator<OrderItem>() {
                    @Override
                    public int compare(OrderItem o1, OrderItem o2) {
                        return o1.googsName.compareTo(o2.googsName);
                    }
                });
                listOrder.add(order);
            }

        } catch (Throwable e) {
            errorCount ++;
            return;
        }

    }

    public List<Order> process(String shopId) {
        List<Order> listShopId = new LinkedList<>();
        for (Order order : listOrder) {
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

    public Map<String, Double> statisticsByShop() {
        Map<String, Double> calculateByShop = new TreeMap<>();
        for (Order order : listOrder) {
            if (calculateByShop.get(order.shopId) == null)
                calculateByShop.put(order.shopId, order.sum);
            else calculateByShop.put(order.shopId, order.sum +
                    calculateByShop.get(order.shopId));
        }
        return calculateByShop;
    }

    public Map<String, Double> statisticsByGoods() {
        Map<String, Double> calculateByGoods = new TreeMap<>();
        for (Order order : listOrder) {
            for (OrderItem orderItem : order.items) {
                if (calculateByGoods.get(orderItem.googsName) == null)
                    calculateByGoods.put(orderItem.googsName, orderItem.price * orderItem.count);
                else calculateByGoods.put(orderItem.googsName, orderItem.price * orderItem.count +
                        calculateByGoods.get(orderItem.googsName));
            }
        }
        return calculateByGoods;
    }

    public Map<LocalDate, Double> statisticsByDay() {
        Map<LocalDate, Double> calculateByDate = new TreeMap<>();
        for (Order order : listOrder) {
            if (calculateByDate.get(order.datetime.toLocalDate()) == null)
                calculateByDate.put(order.datetime.toLocalDate(), order.sum);
            else calculateByDate.put(order.datetime.toLocalDate(), order.sum +
                    calculateByDate.get(order.datetime.toLocalDate()));
        }
        return calculateByDate;
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("d:/orders/01.txt");
        System.out.println(Files.getLastModifiedTime(path));
        LocalDateTime datetime = Files.getLastModifiedTime(path)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        System.out.println(datetime);
        
    }

}