package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LineCount {
    public static int calcEmpty(String fileName) {
            int n = 0;
            try {
                FileReader reader = new FileReader(fileName);
                Scanner scanner = new Scanner(reader);
                try {
                while (scanner.hasNextLine()) {
                        String strFromFile = scanner.nextLine();
                        if (strFromFile.compareTo("") == 0) n++;
                    }
                } finally {
                    reader.close();
                }
            } catch (IOException e) {
                return -1;
            }
            return n; }

    public static void main(String[] args) {
        System.out.println(calcEmpty("myFirstFile.txt"));
    }
}
