package ru.progwards.java1.lessons.sets;

import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class LettersInFile {
    public static String process(String fileName) throws IOException {
        Set<String> setResult = new TreeSet<String>();
        FileReader fileReader = null;
        String resultLine = "";
        int intCode = 0;
        try {
            fileReader = new FileReader(fileName);
            while (intCode != -1) {
                intCode = fileReader.read();
                if (Character.isAlphabetic(intCode)) setResult.add(Character.toString(intCode));
            }
            for(String simbol: setResult) resultLine += simbol;
        } catch (IOException e) {
        throw e;
        } finally {
            fileReader.close();
        }
        return resultLine;
    }

    public static void main(String[] args) {
        try {
            System.out.println(process("myFirstFile.txt"));
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода");
        }
    }
}
