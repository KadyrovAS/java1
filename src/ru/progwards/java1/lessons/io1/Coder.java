package ru.progwards.java1.lessons.io1;

import java.io.*;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {

        int i = 0;
        int intCode = 0;
        FileWriter logFile = null;
        FileReader fileReader = null;
        FileWriter fileWriter = null;

        try {

            fileWriter = new FileWriter(outFileName);
            System.out.println("outFileName = " + outFileName);
            logFile = new FileWriter(logName);
            System.out.println("logName = " + logName);
            fileReader = new FileReader(inFileName);
            System.out.println("inFileName = " + inFileName);

            while (intCode != -1) {
                intCode = fileReader.read();

                if (intCode != -1) {
                    fileWriter.write(code[intCode]);
                }
            }
        } catch (Exception e) {
            try {
                System.out.println("Запись в log = " + e.getMessage());
                logFile.write(e.getMessage());
            } catch (Exception err) {
                return;
            }
        } finally {
            try {
                logFile.close();
                fileReader.close();
                fileWriter.close();
            } catch (Exception e) {
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