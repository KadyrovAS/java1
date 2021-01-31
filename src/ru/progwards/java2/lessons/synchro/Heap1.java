package ru.progwards.java2.lessons.synchro;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

//Потокобезопасный Heap с использованием synchronized

public class Heap1 implements HeapInterface{
    volatile byte[] bytes; //куча
    ConcurrentSkipListMap<Integer,Integer> mapFree = new ConcurrentSkipListMap<>();
    ConcurrentSkipListMap<Integer,Integer> mapBusy = new ConcurrentSkipListMap<>();
    ConcurrentSkipListMap<Integer, Integer> mapMamory = new ConcurrentSkipListMap<>();

    Heap1(int maxHeapSize){
        this.bytes = new byte[maxHeapSize];
        mapFree.put(0,maxHeapSize);
    }

    private int malloc(int size) throws OutOfMemoryException {
        int ptr = 0;
        int dif;

        //Находим блок памяти, который больше или равен size

        for (int i = 0; i < 2; i ++) {
            for (Map.Entry<Integer, Integer> entry : mapFree.entrySet())
                if (entry.getValue() >= size) {
                    ptr = entry.getKey();
                    mapBusy.put(ptr, size);
                    dif = mapFree.get(ptr) - size;
                    mapFree.remove(ptr);
                    if (dif != 0)
                        mapFree.put(ptr + size, dif);

                    mapMamory.put(ptr, ptr);
                    return ptr;
                }
            //Размещение в памяти не получилось
            compact();
        }
        throw new  OutOfMemoryException("Нет свободного блока подходящего размера для " +
                Thread.currentThread().getName());
    }

    @Override
    public synchronized void free(int ptr) throws InvalidPointerException {
        Map.Entry<Integer, Integer> lowerEntry;
        Map.Entry<Integer, Integer> higherEntry;
        if (mapBusy.get(ptr) == null)
            throw new InvalidPointerException("Неверный указатель");
        mapMamory.remove(ptr);

        int size = mapBusy.get(ptr);
        mapBusy.remove(ptr);
        mapFree.put(ptr, size);

        //Ищем свободный блок слева и справа от текущего

        lowerEntry = mapFree.lowerEntry(ptr);
        higherEntry = mapFree.higherEntry(ptr);
        if (lowerEntry != null && lowerEntry.getKey() + lowerEntry.getValue() == ptr) {
            size = lowerEntry.getValue() + mapFree.get(ptr);
            mapFree.remove(ptr);
            ptr = lowerEntry.getKey();
            mapFree.put(ptr, size);
        }
        if (higherEntry != null && ptr + size == higherEntry.getKey()) {
            mapFree.put(ptr, size + higherEntry.getValue());
            mapFree.remove(higherEntry.getKey());
        }
    }

    private void defrag(){
        boolean wasFound = true;
        int ptr=0;
        int size, sizeAll;
        while (wasFound) {
            wasFound = false;
            for (Map.Entry<Integer, Integer> entry: mapFree.entrySet())
                if (mapFree.get(entry.getKey() + entry.getValue()) != null){
                    wasFound = true;
                    ptr = entry.getKey();
                }
            if (wasFound) {
                size = mapFree.get(ptr);
                sizeAll = size + mapFree.get(ptr + size);
                mapFree.remove(ptr + size);
                mapFree.put(ptr, sizeAll);
            }
        }
    }

    private void compact() {
        int ptr = 0;
        int size = 0;
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        for (Map.Entry<Integer, Integer> entry: mapBusy.entrySet()){
            move(entry.getKey(), ptr);
            size = entry.getValue();
            map.put(ptr, size);
            ptr += size;
        }
        mapBusy.clear();
        mapBusy = map;
        mapFree.clear();
        if (ptr < bytes.length - 1)
            mapFree.put(ptr, bytes.length - ptr);
    }

    private void move(int ptrFrom, int ptrTo){
        for (int key: mapMamory.keySet())
            if (mapMamory.get(key) == ptrFrom)
                mapMamory.put(key, ptrTo);


        for (int i = ptrFrom; i < ptrFrom + mapBusy.get(ptrFrom); i ++)
            bytes[i - ptrFrom + ptrTo] = bytes[i];
    }

    @Override
    public String getBytes(int ptr) {
        ptr = mapMamory.get(ptr);
        byte[] byteValue = new byte[mapBusy.get(ptr)];
        for (int i = ptr; i < ptr + mapBusy.get(ptr); i ++)
            byteValue[i - ptr] = this.bytes[i];
        return new String(byteValue);
    }

    private void setBytes(int ptr, byte[] bytes) {
        ptr = mapMamory.get(ptr);
        for (int i = ptr; i < ptr + mapBusy.get(ptr); i ++)
            this.bytes[i] = bytes[i - ptr];
    }

    @Override
    public synchronized int put(String value) throws OutOfMemoryException {
        byte[] batyValue = value.getBytes();
        int ptr = malloc(batyValue.length);
        setBytes(ptr, batyValue);
        return ptr;
    }


}