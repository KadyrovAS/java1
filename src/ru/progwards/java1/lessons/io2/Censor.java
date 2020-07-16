package ru.progwards.java1.lessons.io2;

import java.io.*;
import java.util.Arrays;

public class Censor {

    static class CensorException extends Exception {
        String lineError; //Строка ошибки
        String fileName; //Имя файла

        CensorException(String lineError, String fileName) {
            this.fileName = fileName;
            this.lineError = lineError;
        }

        @Override
        public String toString() {
            return fileName + ":" + lineError;
        }
    }

    private static String[] copyStar(String[] obscene) {
        //Создание obsceneCopy, заполненного "*", кол-во которых равно длине соответствующего элемента obscene
        String[] obsceneCopy = new String[obscene.length];
        StringBuilder copyString = new StringBuilder("");
        int i = 0;
        for (String word : obscene) {
            if (copyString.length() > 0) copyString.delete(0, copyString.length());
            for (int k = 0; k < obscene[i].length(); k++) copyString.append("*");
            obsceneCopy[i] = copyString.toString();
            i++;
        }
        return obsceneCopy;
    }

    public static void censorFile(String inoutFileName, String[] obscene) throws CensorException {

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(inoutFileName, "rw");) {
            String[] obsceneCopy = copyStar(obscene);
            String currentLine;
            long startPosition;
            long endPosition;

            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
            startPosition = randomAccessFile.getFilePointer();
            currentLine = randomAccessFile.readLine();
            endPosition = randomAccessFile.getFilePointer();
            for (int i = 0; i < obscene.length; i ++)
                if (currentLine.contains(obscene[i]))
                    currentLine = currentLine.replaceAll(obscene[i], obsceneCopy[i]);
            randomAccessFile.seek(startPosition);
            randomAccessFile.writeBytes(currentLine);
            randomAccessFile.seek(endPosition);
        }

        } catch (Throwable e) {
            CensorException censorException = new CensorException(e.getMessage(), inoutFileName);
            throw censorException;
        }
    }


    public static void main(String[] args) {
        String[] line = {"IDEA", "most", "help", "one"};
        try {
            censorFile("myFirstFile.txt", line);
        } catch (Throwable e) {
            System.out.println(e);
        }

    }

}
