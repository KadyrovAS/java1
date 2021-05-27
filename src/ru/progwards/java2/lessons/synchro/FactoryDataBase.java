package ru.progwards.java2.lessons.synchro;

public class FactoryDataBase {
    public StoreService createDataBase(String type) {
        if (type.compareTo("file")==0) {
            return FileStoreService.INSTANCE;
        }
        return null;
    }
}
