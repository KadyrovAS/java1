package ru.progwards.java1.lessons.io2;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

//Думаю, что цель была освоить RandomAccessFile. Я его освоил. Проблема возникла с кодировками.
//Прочитать и раскодировать файл у меня получилось, но вот записать обратно в той кодировке, что
//была, не вышло. Поэтому я пошел по пути создания дополнительного файла, который в конце работы
//просто удаляю

public class Censor {

    public static void censorFile(String inoutFileName, String[] obscene) throws Throwable {

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

        String[] obsceneCopy = new String[obscene.length];
        StringBuilder copyString = new StringBuilder("");
        int i = 0;
        for (String word : obscene) {
            //Создание obseneCopy, соответствующего obscene, заполненного "*"
            if (copyString.length() > 0) copyString.delete(0, copyString.length());
            for (int k = 0; k < obscene[i].length(); k++) copyString.append("*");
            obsceneCopy[i] = copyString.toString();
            i++;
        }

        FileReader fileReader = new FileReader(inoutFileName);
        FileWriter fileWriter = new FileWriter("2" + inoutFileName);
        String currentLine;
        try (Scanner scanner = new Scanner(fileReader);) {
            while (scanner.hasNext()) {
                currentLine = scanner.nextLine();
                for (i = 0; i < obscene.length; i++) {
                    if (currentLine.contains(obscene[i]))
                        currentLine = currentLine.replaceAll(obscene[i], obsceneCopy[i]);
                }
                fileWriter.write(currentLine + "\n");
            }


            if (fileWriter != null) fileWriter.close();
            if (fileReader != null) fileReader.close();

            File fileChanged = new File(inoutFileName);
            if (fileChanged.delete()) {
                File fileCopy = new File("2" + inoutFileName);
                fileCopy.renameTo(fileChanged);
            }
        } catch (Throwable e) {
        CensorException censorException = new CensorException(e.getMessage(), inoutFileName);
        throw censorException;
        }
    }


    public static void main(String[] args) {
        String[] line = {"друг", "взоры", "Пора", "Мороз"};
        try {
            censorFile("myFirstFile.txt", line);
        } catch (Throwable e) {
            System.out.println(e);
        }


    }

}
