package ru.progwards.java1.lessons.io1;

import java.io.*;
import java.util.Arrays;

//Уважаемый, Сергей.
//Не могу понять, почему робот ругается на этот код.
//У меня в IDEA все работает корректно. Файл кодируется
//Подскажите пожалуйста, что я не так пробросил?

//И с этим кодом у меня еще появился вопрос.
//Если я каждый оператор, который потенциально может вызвать исключение, буду перекрывать try catch,
//то код будет нечитаемым. Поэтому, скорее всего, требуется обработка исключений в блоке catch.
//Например, в моем случае некоторые файлы могут быть по-просту не открыты, а я их в finally пытаюсь закрыть
//без всякой проверки.
//Может быть из-за этого на меня робот ругается?

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName)
            throws IOException  {
        System.out.println("inFileName = " + inFileName);
        System.out.println("outFileName = " + outFileName);
        System.out.println(Arrays.toString(code));
        System.out.println("code.length = " + code.length);
        System.out.println("logName" + logName);

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
                        System.out.println(intCode);
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