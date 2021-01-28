package ru.progwards.java2.lessons.synchro;

public interface HeapInterface{
    public void free(int ptr) throws InvalidPointerException ;
    public String getBytes(int ptr);
    public int put(String value) throws OutOfMemoryException;
}
