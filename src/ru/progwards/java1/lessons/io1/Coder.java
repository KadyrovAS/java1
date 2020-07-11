package ru.progwards.java1.lessons.io1;

import java.io.*;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName)
            throws IOException  {
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        FileWriter logFile = null;

        int i = 0;
        int intCode = 0;

        try {
            logFile = new FileWriter(logName);
            fileReader = new FileReader(inFileName);
            fileWriter = new FileWriter(outFileName);
            BufferedReader reader = new BufferedReader(fileReader, Character.SIZE );

            try {
                while (intCode != -1) {
                    intCode = reader.read();
                    if (intCode != -1) {
                        fileWriter.write(code[intCode]);
                }
                }
            } catch (IOException e) {
                logFile.write(e.getMessage());
                throw new RuntimeException();
            }
        } catch (IOException e) {
                logFile.write(e.getMessage());
                throw new RuntimeException();
        } finally {
            logFile.close();
            fileReader.close();
            fileWriter.close();
        }

        }

    public static void main(String[] args) throws IOException {
        int n = 65535;
        char[] code = new char[n];
        for (int i = 0; i < n; i ++) {
        code[n - i - 1] = (char) i;
        }
        codeFile("CodeFile.txt","myFirstFile.txt",code,"file_out.log");
    }
}