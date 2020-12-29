package Project;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class DiffClass {
    List<String> originalFile;
    List<String> patchFile;
    List<String> updateFile;
    String pathOriginalFile;
    String pathUpdateFile;
    List<RelationFiles> relFiles = new ArrayList<RelationFiles>();
    class RelationFiles {
        Integer indexLineA; // Индекс в файле A (оригинальный файл)
        Integer indexLineB; //Индекс в айле B (измененный файл)
        RelationFiles(int indexLineA, int indexLineB) {
            this.indexLineA = indexLineA;
            this.indexLineB = indexLineB;
        }
    }
    public void readOriginalFile(String path) throws IOException{
        Path pathFile = Paths.get(path);
        this.pathOriginalFile = path;
        originalFile = new ArrayList<>();
        originalFile = Files.readAllLines(pathFile);
    }

    public void readUpdateFile(String path) throws IOException{
        Path pathFile = Paths.get(path);
        this.pathUpdateFile = path;
        updateFile = new ArrayList<>();
        updateFile = Files.readAllLines(pathFile);
    }

    public void readPatch(String path) throws IOException{
        String[] diffFiles = new String[3];
        String pathFileA, pathFileB;
        Path pathPatch = Paths.get(path);
        patchFile = new ArrayList<>();
        patchFile = Files.readAllLines(pathPatch);
        if (patchFile.get(0).length() > 4 && patchFile.get(0).substring(0,4).compareTo("diff") != 0) return;
        diffFiles = patchFile.get(0).split(" ");
        pathFileA = diffFiles[1].substring(3);
        pathFileA = pathFileA.substring(0, pathFileA.length() - 1);
        pathFileB = diffFiles[2].substring(3);
        pathFileB = pathFileB.substring(0, pathFileB.length() - 1);
        readOriginalFile(pathFileA);
        readUpdateFile(pathFileB);
    }

    public boolean formatDiffMap(HashMap<Integer, List<Integer>> diff) {
        //Map diff содержит: key - номер строки в оригинальном файле, value - список с совпадающими строками в
        //измененном файле
        //если value в diff содержит список из более 1 элемента, тометод оставляет в списке value только один элемент
        //несколько элементов в списке появляются из-за особенностей алгоритма сравнения файлов по-строчно, например
        //пробелы, скобки { или { и т.д.
        List<Integer> listNumLines;
        int numLine;
        int numLineWasFind;
        boolean wasFind;

        for (int i = 0; i < diff.size(); i++) {
            listNumLines = diff.get(i + 1);
            if (listNumLines.size() <= 1) continue;
            //обнаружены повторяющиеся строки (пробелы, скобки и т.д.).
            //проверяем, если в списке listNumLines есть номер предыдущей строки, увеличенной на 1, то оставляем его
            if (i > 0 && diff.get(i).size() == 1) {
                numLine = diff.get(i).get(0);
                wasFind = false;
                for (int numeric: listNumLines)
                    if (numeric == numLine + 1) wasFind = true;
                if (wasFind) {
                    listNumLines.clear();
                    listNumLines.add(numLine + 1);
                    diff.put(i + 1, listNumLines);
                    continue;
                }
            }
            //проверяем, если в списке listNumLines есть номер следующей строки, увеличенной на 1, то оставляем его
            if (i < diff.size() - 1 && diff.get(i + 2).size() == 1) {
                numLine = diff.get(i + 2).get(0);
                wasFind = false;
                for (int numeric: listNumLines)
                    if (numeric == numLine - 1) wasFind = true;
                if (wasFind) {
                    listNumLines.clear();
                    listNumLines.add(numLine - 1);
                    diff.put(i + 1, listNumLines);
                    continue;
                }
            }
            //проверяем, есть ли в других value с одним элементом значения из текущего value
            //если есть, то удаляем их

            for (int k = 0; k < diff.size(); k ++) {
                if (diff.get(k + 1).size() > 1) continue;
                if (diff.get(k + 1).size() == 0) continue;
                numLineWasFind = -1;

                for (int l = 0; l < listNumLines.size(); l++) {
                    if (listNumLines.get(l) != diff.get(k + 1).get(0) &&
                            listNumLines.get(l).compareTo(diff.get(k + 1).get(0)) == 0){
                Integer a1, a2;
                a1 = listNumLines.get(l);
                a2 = diff.get(k + 1).get(0);
                boolean b = a1.equals(a2);
                System.out.println(i + " " + k + " " + l);
            }
//                    if (listNumLines.get(l) == diff.get(k+1).get(0))
                    if (listNumLines.get(l).compareTo(diff.get(k+1).get(0)) == 0)
                        numLineWasFind = l;
                }

                if (numLineWasFind >= 0)
                    listNumLines.remove(numLineWasFind);
            }
        }
        wasFind = true;
        for (int i = 0; i < diff.size(); i++)
            if (diff.get(i + 1).size() > 1)
                wasFind = false;
        return wasFind;
    }

    public int hasLine(HashMap<Integer, List<Integer>> diffMap, int numLine) {
        //возвращает номер строки numLine в diffMap или -1
        for (Map.Entry<Integer, List<Integer>> currentEntry: diffMap.entrySet())
            if (currentEntry.getValue().size() > 0 && currentEntry.getValue().get(0) == numLine)
                return currentEntry.getKey() - 1;
        return -1;
    }

    public boolean formatRelFiles() {
        //список relFiles - это построчная связь между двумя файлами A (оригинальным) и B(отредактированным)
        //indexLineA - индекс строки в файле А
        //indexLineB - индекс соответствующей строки в файле B
        //если один из индексов равен 0, значит строка однозначно удалена (indexLineB = 0),
        //или добавлена (indexLineA = 0) или перемещена.
        //метод после сортировки списка выстраивает эти связи в их логическом порядке

        boolean wasFound;
        int num = 0;
        relFiles.sort(new Comparator<RelationFiles>() {
            @Override
            public int compare(RelationFiles o1, RelationFiles o2) {
                return o1.indexLineB.compareTo(o2.indexLineB);
            }
        });

        if (!addNullLinesInRelFiles()) return false;
        //сформируем новый список, в котором удаленные строки исходного файла будут перемещены на свое место
        moveNullLinesInRelFiles();
        return true;
    }

    private boolean addNullLinesInRelFiles() {
        int num = 0;
        boolean wasFound;
        RelationFiles currentRelation;
        for (int i = 0; i < relFiles.size(); i ++) {
            if (relFiles.get(i).indexLineA == 0) continue;
            if (relFiles.get(i).indexLineB == 0) continue;
            num ++;
            if (relFiles.get(i).indexLineA == num) continue;
            wasFound = false;
            for (int k = 0; k < relFiles.size(); k ++)
                if (relFiles.get(k).indexLineA == num && relFiles.get(k).indexLineB == 0)
                    wasFound = true;
            if (wasFound) {
                i --;
                continue;
            }

            //строка была перемещена
            for (int k = 0; k < relFiles.size(); k ++)
                if (relFiles.get(k).indexLineA == num) {
                    currentRelation = relFiles.get(k);
                    currentRelation.indexLineA = 0;
                    relFiles.set(k, currentRelation);
                    break;
                }
            relFiles.add(new RelationFiles(num,0));
            return false;
        }
        return true;
    }

    private void moveNullLinesInRelFiles() {
        List<RelationFiles> relFilesSorted = new ArrayList<>();
        int num = 0;
        int minus;

        for (int i = 0; i < relFiles.size(); i ++) {
            if (relFiles.get(i).indexLineB == 0) continue;
            if (relFiles.get(i).indexLineA == 0) {
                relFilesSorted.add(relFiles.get(i));
                continue;
            }
            num ++;

            if (findNumInRelFiles(num) != null) {
                relFilesSorted.add(findNumInRelFiles(num));
                i --;
                continue;
            }
            relFilesSorted.add(relFiles.get(i));
        }

        minus = relFiles.size() - relFilesSorted.size();
        if (minus != 0)
            for (int i = 0; i < minus; i ++) {
                num ++;
                relFilesSorted.add(findNumInRelFiles(num));
            }

        relFiles = relFilesSorted;
    }

    private RelationFiles findNumInRelFiles(int num) {
        boolean wasFound = false;
        for (int k = 0; k < relFiles.size(); k ++)
            if (relFiles.get(k).indexLineA == num && relFiles.get(k).indexLineB == 0)
                return relFiles.get(k);
        return null;
    }

    private void diff(String path) { //сравнивает 2 файла и формирует патч по ссылке path
        HashMap<Integer, List<Integer>> diffMap = new HashMap<>(); //карта соответствия файлов
        List<Integer> listNumLines;
        Path pathPatch = Paths.get(path);
        int countLines = 0;

        for (int i = 0; i < originalFile.size(); i++) {
            countLines++;

            listNumLines = new ArrayList<>();
            for (int k = 0; k < updateFile.size(); k++)
                if (originalFile.get(i).compareTo(updateFile.get(k)) == 0)
                    listNumLines.add(k + 1);
            diffMap.put(countLines, listNumLines);
        }

//        for (Map.Entry currentEntry: diffMap.entrySet())
//            System.out.println(currentEntry.getKey() + " " + currentEntry.getValue());

        for (int i = 0; i < 100; i ++)
            if (formatDiffMap(diffMap)) break; //пока не знаю, как оптимизировать этот блок
                                               //по логике достаточно 2 вызовов formatDiffMap


//        for (Map.Entry currentEntry: diffMap.entrySet())
//            System.out.println(currentEntry.getKey() + " " + currentEntry.getValue());

        for (int i = 0; i < diffMap.size(); i ++)
            if (diffMap.get(i + 1).size() == 0)
                relFiles.add(new RelationFiles(i + 1, 0));
            else
                relFiles.add(new RelationFiles(i + 1, diffMap.get(i + 1).get(0)));

        for (int i = 0; i < updateFile.size(); i ++)
            if (hasLine(diffMap, i +1) < 0)
                relFiles.add(new RelationFiles(0, i + 1));

        while (true)
            if (formatRelFiles()) break;
                    
        //Формирование патч-файла
        createPatch(relFiles, pathPatch);
    }

    public void createPatch(List<RelationFiles> relFiles, Path pathPatch) {
        List<String> listLinesUpdate = new ArrayList<>();
        int indexLine;
        int startB = 0;
    try {
        Files.writeString(pathPatch, "diff " + '"' + "a/" + this.pathOriginalFile + '"' + " " +
                '"' + "b/" + pathUpdateFile +'"' + "\n");
        Files.writeString(pathPatch, "--- " + '"' + "a/" + this.pathOriginalFile + '"' + "\n",
                StandardOpenOption.APPEND);
        Files.writeString(pathPatch, "+++ " + '"' + "b/" + this.pathUpdateFile + '"' + "\n",
                StandardOpenOption.APPEND);
        int numLine = 0;
        int countLineDel;
        int countLineAdd;

//        for (int i = 0; i < relFiles.size(); i ++)
//            System.out.println(relFiles.get(i).indexLineA + " " + relFiles.get(i).indexLineB);

        //формирование патча
        for (int i = 0; i < relFiles.size(); i ++) {
            if (relFiles.get(i).indexLineA != 0) numLine ++;
            if (relFiles.get(i).indexLineB != 0) startB = relFiles.get(i).indexLineB;
            if (relFiles.get(i).indexLineA ==0 || relFiles.get(i).indexLineB == 0) {
                //начало блока, который был отредактирован
                countLineAdd = 0;
                countLineDel = 0;

                listLinesUpdate.clear();
                //считаем количество вставленных и удаленных строк
                for (int k = i; k < relFiles.size(); k ++)
                    if (relFiles.get(k).indexLineA == 0) {
                        if (k == relFiles.size() - 1) i = k;
                        countLineAdd++;
                        indexLine = relFiles.get(k).indexLineB - 1;
                        listLinesUpdate.add("+" + updateFile.get(indexLine));
//                        startB = relFiles.get(k).indexLineB;
                    }
                    else if (relFiles.get(k).indexLineB == 0) {
                        if (k == relFiles.size() - 1) i = k;
                        countLineDel++;
//                        numLine = relFiles.get(k).indexLineA;
                        indexLine = relFiles.get(k).indexLineA - 1;
                        listLinesUpdate.add("-" + originalFile.get(indexLine));
                    }
                    else {
                        i = k - 1;
                        break;
                    }
                String line = "";
                line = "@@ -" + String.valueOf(numLine) + "," + String.valueOf(countLineDel) + " ";
                line += "+" + String.valueOf(startB) + "," +
                        String.valueOf(countLineAdd) + " @@" + "\n";
                Files.writeString(pathPatch, line, StandardOpenOption.APPEND);
                for (String updateLine: listLinesUpdate)
                    Files.writeString(pathPatch, updateLine + "\n", StandardOpenOption.APPEND);
            }
        }

    } catch (IOException e) {}
    }

    public static void main(String[] args) {
        String pathOriginal = "d:/java/project/1.java";
        String pathUpdate = "d:/java/project/2.java";
        String pathPatch = "d:/java/project/12.patch";
        DiffClass diffClass = new DiffClass();
        try {
            diffClass.readOriginalFile(pathOriginal); //Загружаем оригинальный файл
            diffClass.readUpdateFile(pathUpdate); //Загружаем файл после обновления
            diffClass.diff(pathPatch); //Сравниваем 2 файла
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
