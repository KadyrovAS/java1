package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SalesInfo {
    class OneRecord {
        String customerName; //Имя покупателя
        String productName; //Наименование товара
        int amount; //Количество товара
        double value; //Сумма за покупку

        OneRecord(String customerName, String productName, int amount, double value) {
            this.customerName = customerName;
            this.productName = productName;
            this.amount = amount;
            this.value = value;
        }
    }
    private   List<OneRecord> listRecords = new LinkedList<OneRecord>();
    public int loadOrders(String fileName) { //возвращает количество успешно загруженных строк
        String record;
        String[] item;
        listRecords.clear();

        try (FileReader fileReader = new FileReader(fileName);) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                record = scanner.nextLine();
                item = record.split(",");
                for (int i = 0; i < item.length; i ++) item[i] = item[i].trim();
                try {
                    listRecords.add(new OneRecord(item[0], item[1],
                            Integer.valueOf(item[2]), Double.valueOf(item[3])));
                } catch (NumberFormatException e) {
                    continue;
                }
            }

        } catch (IOException e) {
            return 0;
        }
        return listRecords.size();
    }

    public Map<String, Double> getGoods() { //вернуть список товаров, отсортированный по наименованию товара
        Map <String, Double> mapResult = new TreeMap<String, Double>();
        Double sum;
        for (OneRecord oneRecord: listRecords) {
            if (mapResult.get(oneRecord.productName) == null) mapResult.put(oneRecord.productName, oneRecord.value);
            else {
                sum = mapResult.get(oneRecord.productName);
                mapResult.put(oneRecord.productName, oneRecord.value + sum);
            }
        }
        return mapResult;
    }

    public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers() {
        Map<String, AbstractMap.SimpleEntry<Double, Integer>> mapResult = new TreeMap<>();

        Double sum;
        Integer nAmount;
        for (OneRecord oneRecord: listRecords) {
            if (mapResult.get(oneRecord.customerName) == null)
                mapResult.put(oneRecord.customerName,new AbstractMap.SimpleEntry<>(oneRecord.value, oneRecord.amount));
            else {
                sum = mapResult.get(oneRecord.customerName).getKey();
                nAmount = mapResult.get(oneRecord.customerName).getValue();
                sum += oneRecord.value;
                nAmount += oneRecord.amount;
                mapResult.put(oneRecord.customerName, new AbstractMap.SimpleEntry<>(sum, nAmount));
            }
        }
        return mapResult;
    }

    public static void main(String[] args) {
        SalesInfo salesInfo = new SalesInfo();
        System.out.println(salesInfo.loadOrders("SalesInfo.csv"));
        System.out.println(salesInfo.getGoods());
        System.out.println(salesInfo.getCustomers());
    }
}
