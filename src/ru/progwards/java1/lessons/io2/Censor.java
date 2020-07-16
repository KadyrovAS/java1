package ru.progwards.java1.lessons.io2;

import java.io.FileInputStream;
import java.io.RandomAccessFile;


public class Censor {

    public static void censorFile(String inoutFileName, String[] obscene)
    throws Throwable {

        StringBuilder stringBuilder = new StringBuilder("");
        class CensorException extends Exception {
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

        try (FileInputStream fileInputStream = new FileInputStream(inoutFileName);) {
        //Проверка наличия файла inoutFileName

        if (fileInputStream != null) fileInputStream.close();
    } catch (Throwable e) {
        CensorException censorException = new CensorException(e.getMessage(), inoutFileName);
        throw censorException;
        }
        String currentLine;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(inoutFileName, "rw");) {

            long beforeread = 0; //положение курсора до чения файла
            long afterRead = 0; //положение курсора после чтения файла

            while (randomAccessFile.getFilePointer()<randomAccessFile.length()) {

                beforeread = randomAccessFile.getFilePointer();
                currentLine = randomAccessFile.readLine();
                afterRead = randomAccessFile.getFilePointer();


//                currentLine = new String(currentLine.getBytes("ISO-8859-1"), "UTF-8");

                for (String word: obscene) {
                    if (currentLine.contains(word)) {
                        stringBuilder.delete(0, stringBuilder.length());
                        for (int i = 0; i < word.length(); i ++) stringBuilder.append("*");
                        currentLine= currentLine.replaceAll(word, stringBuilder.toString());
                    }
                }

                System.out.println(currentLine);
                randomAccessFile.seek(beforeread);

                randomAccessFile.writeBytes(currentLine);
                randomAccessFile.seek(afterRead);
            }
        }
    }

    public static void main(String[] args) {
        String[] line = {"солнце", "друг", "03"};
        try {
            censorFile("myFirstFile.txt", line);
        } catch (Throwable e) {
            System.out.println(e);
        }


    }

}
