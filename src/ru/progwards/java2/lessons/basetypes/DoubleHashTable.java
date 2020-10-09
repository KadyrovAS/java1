package ru.progwards.java2.lessons.basetypes;

public class DoubleHashTable <K, V> {
//При удалении item из таблицы ячейку как удаленную не помечал. Не понял как это работает
//Предполагается, что запрашивать item с несуществующим key не будут. Защиты от зацикливания не делал

    private int nСollisions; //количество коллизий
    private int nAllRequest; //общее количество запросов
    private final int ALLOWCOLLISIONS = 10; //доля коллизий в процентах

    class HashItem <K,V>{
        private K key;
        private V item;

        public HashItem(K key, V item) {
            this.key = key;
            this.item = item;
        }
        K getKey() {return this.key;}
        V getItem() {return this.item;}
        void setKey(K key) {this.key = key;}
    }

    HashItem[] table;
    DoubleHashTable(int n) {
        table = new HashItem[n];
        nAllRequest = 0;
        nСollisions = 0;
    }

    public <K> int getHash1(K key) {
        int res = (Integer) key % table.length;
        return res;
    }

    public <K> int getHash2(K key) {
        double k = (Integer)key * Math.PI;
        k = k - (int) k;
        int res = (int)(k * table.length);
        return res;
    }

    public <K,V> void toIncreaseTable(K newKey, V newValue) {
        int n = simpleValue(table.length * 2);
        int index, index1, index2;
        nAllRequest = 0;
        nСollisions = 0;
        HashItem[] table2 = new HashItem[n];
        for (HashItem value: table){
            if (value == null) continue;
            index1 = getHash1(value.key);
            if (table2[index1] == null) table2[index1] = value;
            else {
                index2 = getHash2(value.key);
                index = index1;
                do {
                    index += index2;
                    if (index >= table2.length) index -=table2.length;
                } while (table2[index] != null);
                table2[index] = value;
            }
        }

        table = table2;
        add(newKey, newValue);
    }

    public <K, V> void add(K key, V value) { // добавить пару ключ-значение
        int index1 = getHash1(key);
        int index, index2;
        nAllRequest++;
        if (table[index1] == null) table[index1] =  new HashItem(key, value);
        else {
            index2 = getHash2(key);
            index = index1;
            do {
                nСollisions++;
                if (nСollisions / nAllRequest * 100 >= ALLOWCOLLISIONS) {
                    toIncreaseTable(key, value);
                    return;
                }
                index += index2;
                if (index >= table.length) index -= table.length;
            } while (table[index] != null);
            table[index] = new HashItem(key, value);
        }
    }

    private static boolean isSimpleValue(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= num/2; i ++)
            if (num % i == 0) return false;
        return true;
    }

    private static <K extends Number> int simpleValue (K num) {
        //возвращает ближайшее простое число
        Integer intNum = (Integer) num;
        while (!isSimpleValue(intNum)) intNum++;
        return intNum;
    }

    public V get(K key) { // получить значение по ключу
        int index = findIndex(key);
        return (V) table[index].getItem();
    }

    private int findIndex(K key) {
        int index, index2;
        int index1 = getHash1(key);
        if (table[index1] != null && table[index1].getKey() == key) return index1;
        index2 = getHash2(key);
        index = index1;
        do {
            index += index2;
            if (index >= table.length) index -= table.length;
            if (table[index] != null && table[index].getKey() == key) return index;
        } while (true);
    }

    public void remove(K key) { // удалить элемент по ключу
        int index = findIndex(key);
        table[index] = null;
    }

    public void change(K key1, K key2) { // изменить значение ключа у элемента с key1 на key2
        int index = findIndex(key1);
        HashItem value = table[index];
        table[index] = null;
        value.setKey(key2);
        add(value.key, value.item);
    }

    public int size() { // получить количество элементов
        int nCount = 0;
        for (HashItem value: table)
            if (value != null) nCount++;
        return nCount;
    }

    public static void main(String[] args) {
        int n = simpleValue(14);
        DoubleHashTable table = new DoubleHashTable<>(n);
        for (int i = 0; i < 100; i++)
            table.add(i, i);

        table.change(0,100);

        for (int i = 1; i <= 100; i++)
            System.out.println(table.get(i) + "   " + table.findIndex(i));
    }
    }