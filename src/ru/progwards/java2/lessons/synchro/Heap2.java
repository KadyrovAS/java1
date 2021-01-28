package ru.progwards.java2.lessons.synchro;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReadWriteLock;

//Потокобезопасный Heap с использованием ReadWriteLock
public class Heap2 implements HeapInterface{
    volatile byte[] bytes; //куча
    TreeMap<Integer,Integer> mapFree = new TreeMap<>();
    TreeMap<Integer,Integer> mapBusy = new TreeMap<>();
    TreeMap<Integer, Integer> mapMamory = new TreeMap<>();
    ReadWriteLock lock;

    Heap2(int maxHeapSize, ReadWriteLock lock){
        this.bytes = new byte[maxHeapSize];
        this.lock = lock;
        mapFree.put(0,maxHeapSize);
    }

    private int malloc(int size) throws OutOfMemoryException {
        int ptr = 0;
        int dif;

        //Находим блок памяти, который больше или равен size

        lock.writeLock().lock();
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
                    lock.writeLock().unlock();
                    return ptr;
                }
            //Размещение в памяти не получилось
            compact();
        }
        lock.writeLock().unlock();
        throw new  OutOfMemoryException("Нет свободного блока подходящего размера для " +
                Thread.currentThread().getName());
    }

    @Override
    public void free(int ptr) throws InvalidPointerException {
        lock.writeLock().lock();
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
        lock.writeLock().unlock();
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

        lock.writeLock().lock();
        TreeMap<Integer, Integer> map = new TreeMap<>();
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
        lock.writeLock().unlock();
    }

    private void move(int ptrFrom, int ptrTo){
        lock.writeLock().lock();
        for (int key: mapMamory.keySet())
            if (mapMamory.get(key) == ptrFrom)
                mapMamory.put(key, ptrTo);


        for (int i = ptrFrom; i < ptrFrom + mapBusy.get(ptrFrom); i ++)
            bytes[i - ptrFrom + ptrTo] = bytes[i];
        lock.writeLock().unlock();
    }

    @Override
    public String getBytes(int ptr) {
        lock.readLock().lock();
        ptr = mapMamory.get(ptr);
        byte[] byteValue = new byte[mapBusy.get(ptr)];
        for (int i = ptr; i < ptr + mapBusy.get(ptr); i ++)
            byteValue[i - ptr] = this.bytes[i];
        lock.readLock().unlock();
        return new String(byteValue);
    }

    private void setBytes(int ptr, byte[] bytes) {
        lock.writeLock().lock();
        ptr = mapMamory.get(ptr);
        for (int i = ptr; i < ptr + mapBusy.get(ptr); i ++)
            this.bytes[i] = bytes[i - ptr];
        lock.writeLock().unlock();
    }

    @Override
    public int put(String value) throws OutOfMemoryException {
        byte[] batyValue = value.getBytes();
        int ptr = malloc(batyValue.length);
        setBytes(ptr, batyValue);
        return ptr;
    }


}