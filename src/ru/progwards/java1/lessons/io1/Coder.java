package ru.progwards.java1.lessons.io1;

import java.io.*;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {
        System.out.println("Start");
        int i = 0;
        int intCode = 0;
        FileWriter logFile = null;
        FileReader fileReader = null;
        FileWriter fileWriter = null;

        try {
            logFile = new FileWriter(logName);
            System.out.println("logName = " + logName);

            fileWriter = new FileWriter(outFileName);
            System.out.println("outFileName = " + outFileName);

            fileReader = new FileReader(inFileName);
            System.out.println("inFileName = " + inFileName);

            while (intCode != -1) {
                intCode = fileReader.read();
                System.out.println("Чтение из fileReader: " + intCode);
                if (intCode != -1) {
                    fileWriter.write(code[intCode]);
                    System.out.println("запись в fileWriter: " + intCode);
                }
            }
        } catch (Exception e) {
            try {
                System.out.println("Запись в log = " + e.getMessage());
                System.out.println("logFile = " + logFile);
                logFile.write(e.getMessage());
            } catch (Exception err) {
                if (logFile == null)
                    System.out.println("logFile == null");
                System.out.println("В log не записалось");
                return;
            }
        } finally {
            try {
                if (logFile != null)
                logFile.close();
                System.out.println("log закрылся");
                if (fileReader != null)
                fileReader.close();
                System.out.println("fileReader закрылся");
                if (fileWriter != null)
                fileWriter.close();
                System.out.println("fileWriter закрылся");
            } catch (Exception e) {
                System.out.println("Файлы закрылись с ошибкой");
                return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int n = Character.MAX_VALUE;
        char[] code = new char[n];
        for (int i = 0; i < n; i++) {
            code[n-i-1] = (char) i;
        }
        codeFile("CodeFil.txt", "myFirstFile.txt", code, "file_out.log");
    }


}