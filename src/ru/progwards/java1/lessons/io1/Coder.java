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
            BufferedWriter writer = new BufferedWriter(fileWriter, Integer.SIZE);

            try {
                while (intCode != -1) {
                    intCode = reader.read();
                    if (intCode != -1) for (i = 0; i < code.length; i ++) {
                        if (code[i] == (char) intCode) {
                            writer.write(i);
                            break;
                        }
                    }

                }
            } catch (IOException e) {
                logFile.write(e.getMessage());
            }
        } catch (IOException e) {
                logFile.write(e.getMessage());
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
        code[i] = (char) i;
        }
        codeFile("myFirstFile.txt","CodeFile.txt",code,"file_out.log");
    }
}