package ru.progwards.java1.lessons.sort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ExternalSort2{
    static final String PATH_DIRECTORY = "c:/work/own/java/data/";
    static final int ARRAY_SIZE = 10_000;

    static int countFiles = 0;
    static int[] ar = new int[ARRAY_SIZE];
    static int countArray = 0;

    static void sort(String inFileName, String outFileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFileName));
            reader.lines().forEach(value -> {
                try {
                    addArray(Integer.valueOf(value), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (countArray > 0)
                addArray(0, true);
            reader.close();
            mergeFiles(0, countFiles - 1);
            Path path = Paths.get(PATH_DIRECTORY + "temp" + String.valueOf(countFiles) + ".txt");
            Path pathOut = Paths.get(outFileName);
            Files.move(path, pathOut, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void addArray(int value, boolean notFull) throws IOException {
        if (!notFull) ar[countArray++] = value;
        if (countArray == ARRAY_SIZE || notFull) {
            Hoare.sortHoare(ar, 0, countArray - 1);
            writeSort(ar, countArray);
            countArray = 0;
        }
    }

    static void writeSort(int[] ar, int arraySize) throws IOException {
        //Запись отсортированных массивов в соответствующие временные файлы
        String tempOutPath = PATH_DIRECTORY + "temp" + String.valueOf(countFiles++) + ".txt";
        Path path = Paths.get(tempOutPath);
        String valuesString = "";
        for (int i = 0; i < arraySize; i++)
            valuesString += String.valueOf(ar[i]) + "\n";
        Files.writeString(path, valuesString);
    }

    static void mergeFiles(int start, int finis) throws IOException {
        int countLocal = 0; //счетчик открытых файлов
        int countAll = start; //счетчик всех файлов
        int overSize = 0; // счетчик добавленных временных файлов

        Path path;
        String fileName;
        BufferedReader[] readers = new BufferedReader[ARRAY_SIZE];
        while (true) {
            while (countAll <= finis) {
                fileName = PATH_DIRECTORY + "temp" + String.valueOf(countAll++) + ".txt";
                readers[countLocal++] = new BufferedReader(new FileReader(fileName));
                if (countLocal == ARRAY_SIZE || countAll > finis) {
                    merge(readers, countLocal, overSize++);
                    countLocal = 0;
                }
            }
            for (int i = start; i <= finis; i++) {
                fileName = PATH_DIRECTORY + "temp" + String.valueOf(i) + ".txt";
                path = Paths.get(fileName);
                Files.deleteIfExists(path);
            }
            if (overSize > 1) {
                countFiles += overSize;
                mergeFiles(finis + 1, finis + overSize);
                return;
            }
            else
                break;
        }
    }
    static void merge(BufferedReader[] readers, int size, int overSize) throws IOException {
        List<Integer> list = new ArrayList<>();
        int min;
        int indexMin;
        String fileName = PATH_DIRECTORY + "temp" + String.valueOf(countFiles + overSize) + ".txt";

        Path path = Paths.get(fileName);
        Files.writeString(path, "");

        for (int i = 0; i < size; i++)
            list.add(Integer.valueOf(readers[i].readLine()));

        boolean wasReadedAll = true;

        while (true) {
            min = Integer.MAX_VALUE;
            indexMin = 0;
            wasReadedAll = false;
            for (int i = 0; i < size; i++)
                if (list.get(i) != null) {
                    wasReadedAll = true;
                    if (list.get(i) <= min) {
                        min = list.get(i);
                        indexMin = i;
                    }
                }

            if (!wasReadedAll)
                break;
            //Записываем найденный минимальный элемент в файл
            Files.writeString(path, String.valueOf(min) + "\n", StandardOpenOption.APPEND);

            if (readers[indexMin].ready())
                list.set(indexMin, Integer.valueOf(readers[indexMin].readLine()));
            else {
                list.set(indexMin, null);
                readers[indexMin].close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String inFileName = PATH_DIRECTORY + "data.txt";
        String outFileName = PATH_DIRECTORY + "SortData.txt";
        long time = System.currentTimeMillis();
        sort(inFileName, outFileName);
        System.out.println(System.currentTimeMillis() - time);
    }
}
