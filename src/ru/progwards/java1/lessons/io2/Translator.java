package ru.progwards.java1.lessons.io2;

import java.util.Arrays;

public class Translator {
    private String[] inLang;
    private String[] outLang;

    Translator(String[] inLang, String[] outLang) {
        this.inLang = new String[inLang.length];
        this.outLang = new String[outLang.length];

        for (int i = 0; i < inLang.length; i++) this.inLang[i] = inLang[i].toLowerCase();
        for (int i = 0; i < outLang.length; i++) this.outLang[i] = outLang[i].toLowerCase();
    }

    private String compareWord(String word) {
        //находит слово word в словаре inLang и возвращает соответствующее из outLang
        //при необходимости первую букву слова транслирует в верхний регистр
        //если word в inLang не оказалось, возвращается word
        char[] sim;
        for (int i = 0; i < inLang.length; i++) {

            if (word.toLowerCase().compareTo(inLang[i]) == 0) {
                sim = word.substring(0, 1).toCharArray();
                if (Character.isUpperCase(sim[0])) {
                    //первая буква слова должна быть в верхнем регистре
                    sim = outLang[i].substring(0, 1).toCharArray();
                    return Character.toUpperCase(sim[0]) + outLang[i].substring(1);
                } else return outLang[i];
            }
        }
        return word;
    }

    public String translate(String sentence) {
        //выделяет слова из текста и отправляет на перевод в compareWord
        //все символы, не являющиеся буквами, добавляются к результирующей строке без изменений
        StringBuilder resultLine = new StringBuilder("");
        int wordIndexStart = 0;
        int wordIndexEnd = 0;
        char[] sim;
        String word;
        for (int i = 0; i < sentence.length(); i++) {
            sim = sentence.substring(i, i + 1).toCharArray();
            if (Character.isAlphabetic(sim[0])) wordIndexEnd++;
            else if (wordIndexStart == wordIndexEnd) {
                resultLine.append(sim[0]);
                wordIndexStart++;
                wordIndexEnd++;
            } else {
                word = sentence.substring(wordIndexStart, wordIndexEnd);
                resultLine.append(compareWord(word));
                resultLine.append(sim[0]);
                wordIndexEnd++;
                wordIndexStart = wordIndexEnd;
            }

        }
        return resultLine.toString();
    }

    public static void main(String[] args) {
        String[] arIn = {"Дом", "Машина", "Улица", "Поле", "и"};
        String[] arOut = {"House", "Car", "Street", "Field", "and"};
        Translator translator = new Translator(arIn, arOut);
        System.out.println(translator.translate("(Машина) и дом."));
    }
}
