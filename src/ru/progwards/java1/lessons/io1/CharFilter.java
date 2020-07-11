package ru.progwards.java1.lessons.io1;

import java.io.*;

public class CharFilter {
    public static void filterFile(String inFileName, String outFileName, String filter) throws IOException {
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        boolean eq;
        int intCode = 0;

        try {
            fileReader = new FileReader(inFileName);
            fileWriter = new FileWriter(outFileName);
            BufferedReader reader = new BufferedReader(fileReader, Character.SIZE );

            try {
                while (true) {
                    intCode = reader.read();
                    eq = false;
                    if (intCode != -1) for (int i = 0; i < filter.length(); i ++) {
                        if ((char) filter.charAt(i) == (char) intCode) eq = true;
                    } else break;

                        if (! eq) {
                            fileWriter.write((char) intCode);
                        }

                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            fileReader.close();
            fileWriter.close();
        }

    }

    public static void main(String[] args) throws IOException {
        filterFile("myFirstFile.txt","CodeFile.txt","о");
    }
}
