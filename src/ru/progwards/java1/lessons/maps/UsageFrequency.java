package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UsageFrequency {
    private String textFromFile;

    public void processFile(String fileName) {
        int intCode = 0;
        this.textFromFile = "";
        try (FileReader fileReader = new FileReader(fileName);) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) textFromFile += scanner.nextLine() + "\n";
        } catch (IOException e) {
            return;
        }
    }

    public Map<Character, Integer> getLetters() {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int n;
        for (Character character: textFromFile.toCharArray())
            if (Character.isLetter(character) || Character.isDigit(character)) {
                if (map.get(character) == null) map.put(character, 1);
                else {
                    n=map.get(character);
                    map.put(character, ++n);
                }
            }

        return map;
    }

    public Map<String, Integer> getWords() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        int n;
        Set <String> setSimbols = new HashSet<>();
        for (Character character: textFromFile.toCharArray())
            if (!Character.isLetter(character) && !Character.isDigit(character))
                setSimbols.add(String.valueOf(character));
         for (String simbol: setSimbols)
             textFromFile = textFromFile.replace(simbol, " ");

        for (String word: textFromFile.split(" ")) {
            if (word.trim().compareTo("") == 0) continue;
            if (map.get(word) == null) map.put(word, 1);
            else {
                n=map.get(word);
                map.put(word, ++n);
            }
        }

        return map;
    }

    public static void main(String[] args) {
        UsageFrequency usageFrequency = new UsageFrequency();
        usageFrequency.processFile("wiki.train.tokens");
        System.out.println(usageFrequency.getLetters());
        System.out.println(usageFrequency.getWords());
    }
}
