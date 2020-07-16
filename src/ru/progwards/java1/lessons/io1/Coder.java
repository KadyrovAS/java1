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
            logFile = new FileWriter(logName);
            fileReader = new FileReader(inFileName);
            fileWriter = new FileWriter(outFileName);


            while (intCode != -1) {
                intCode = fileReader.read();
                if (intCode != -1) {
                    fileWriter.write(code[intCode]);
                }
            }
        } catch (Throwable e) {
            try {
                logFile.write(e.getMessage());
            } catch (Throwable err) {
                return;
            }
        } finally {
            try {
                if (logFile != null) logFile.close();
                if (fileReader != null) fileReader.close();
                if (fileWriter != null) fileWriter.close();
            } catch (Throwable e) {
                return;
            }
        }
    }

    public static void main(String[] args)  {
        int n = Character.MAX_VALUE;
        char[] code = new char[n];
        for (int i = 0; i < n; i++) {
            code[i] = (char) i;
        }
        codeFile("CodeFile.txt", "myFirstFile.txt", code, "file_out.log");
    }

}