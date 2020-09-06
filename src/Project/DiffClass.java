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
        Integer indexLineA; // Индекс в файле A
        Integer indexLineB; //Индекс в айле B
        RelationFiles(int indexLineA, int indexLineB) {
            this.indexLineA = indexLineA;
            this.indexLineB = indexLineB;
        }
    }
    public void readOriginalFile(String path) {
        Path pathFile = Paths.get(path);
        this.pathOriginalFile = path;
        originalFile = new ArrayList<>();
        try {
            originalFile = Files.readAllLines(pathFile);
        } catch (IOException e) {}

    }

    public void readUpdateFile(String path) {
        Path pathFile = Paths.get(path);
        this.pathUpdateFile = path;
        updateFile = new ArrayList<>();
        try {
            updateFile = Files.readAllLines(pathFile);
        } catch (IOException e) {}

    }

    public void readPatch(Path path) {
        patchFile = new ArrayList<>();
        try {
            patchFile = Files.readAllLines(path);
        } catch (IOException e) {}

    }

    public boolean formatDiffMap(HashMap<Integer, List<Integer>> diff) {
        //если value diffMap содержит список из более 1 элемента, то оставляет только один элемент
        List<Integer> listNumLines;
        int numLine;
        int numLineWasFind;
        boolean wasFind;
        for (int i = 0; i < diff.size(); i++) {
            listNumLines = diff.get(i + 1);
            if (listNumLines.size() == 1) continue;
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
                for (int l = 0; l < listNumLines.size(); l ++)
                    if (listNumLines.get(l) == diff.get(k + 1).get(0))
                        numLineWasFind = l;
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
        for (Map.Entry<Integer, List<Integer>> currentEntry: diffMap.entrySet())
            if (currentEntry.getValue().size() > 0 && currentEntry.getValue().get(0) == numLine)
                return currentEntry.getKey() - 1;
        return -1;
    }

    public boolean formatRelFiles() {
        RelationFiles currentRelation;
        List<RelationFiles> relFilesSorted = new ArrayList<>();
        boolean wasFound;
        int num = 0;
        relFiles.sort(new Comparator<RelationFiles>() {
            @Override
            public int compare(RelationFiles o1, RelationFiles o2) {
                return o1.indexLineB.compareTo(o2.indexLineB);
            }
        });

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

        //сформируем новый список, в котором удаленные строки исходного файла будут перемещены на свое место
        num = 0;
        for (int i = 0; i < relFiles.size(); i ++) {
            if (relFiles.get(i).indexLineB == 0) continue;
            if (relFiles.get(i).indexLineA == 0) {
                relFilesSorted.add(relFiles.get(i));
                continue;
            }
            num ++;

            wasFound = false;
            for (int k = 0; k < relFiles.size(); k ++)
                if (relFiles.get(k).indexLineA == num && relFiles.get(k).indexLineB == 0) {
                    wasFound = true;
                    relFilesSorted.add(relFiles.get(k));
                    i --;
                    break;
                }
            if (wasFound) continue;
            relFilesSorted.add(relFiles.get(i));
        }
        relFiles = relFilesSorted;
        return true;
    }

    public void diff(String path) { //сравнивает 2 файла
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

        for (int i = 0; i < 10; i ++)
            if (formatDiffMap(diffMap)) break; //пока не знаю, как оптимизировать этот блок
                                               //по логике достаточно 2 вызовов formatDiffMap

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

//            for (RelationFiles rf: relFiles)
//                System.out.println(rf.indexLineA + " " + rf.indexLineB);

        //Формирование патч-файла
        createPatch(relFiles, pathPatch);

    }

    public void createPatch(List<RelationFiles> relFiles, Path pathPatch) {
        List<String> listLinesUpdate = new ArrayList<>();
        int indexLine;
        int startB = 0;
    try {
        Files.writeString(pathPatch, "diff " + '"' + "a/" + this.pathOriginalFile + '"' + " " +
                '"' + pathUpdateFile +'"' + "\n");
        Files.writeString(pathPatch, "--- " + '"' + "b/" + this.pathOriginalFile + '"' + "\n",
                StandardOpenOption.APPEND);
        Files.writeString(pathPatch, "+++ " + '"' + this.pathUpdateFile + '"' + "\n",
                StandardOpenOption.APPEND);
        int numLine = 0;
        int countLineDel;
        int countLineAdd;

        //формирование патча
        for (int i = 0; i < relFiles.size(); i ++) {
            if (relFiles.get(i).indexLineA != 0) numLine ++;
            if (relFiles.get(i).indexLineB != 0) startB = relFiles.get(i).indexLineB;
            if (relFiles.get(i).indexLineA ==0 || relFiles.get(i).indexLineB == 0) {
                //либо вставили строку, либо удалили строку
                countLineAdd = 0;
                countLineDel = 0;

                listLinesUpdate.clear();
                //считаем количество вставленных и удаленных строк
                for (int k = i; k < relFiles.size(); k ++)
                    if (relFiles.get(k).indexLineA == 0) {
                        countLineAdd++;
                        indexLine = relFiles.get(k).indexLineB - 1;
                        listLinesUpdate.add("+" + updateFile.get(indexLine));
                        startB = relFiles.get(k).indexLineB;
                    }
                    else if (relFiles.get(k).indexLineB == 0) {
                        countLineDel++;
                        numLine = relFiles.get(k).indexLineA;
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
        String pathOriginal = "d:/java/project/TestJava1.java";
        String pathUpdate = "d:/java/project/TestJava2.java";
        String pathPatch = "d:/java/project/TestJava.patch";
        DiffClass diffClass = new DiffClass();
        diffClass.readOriginalFile(pathOriginal);
        diffClass.readUpdateFile(pathUpdate);
        diffClass.diff(pathPatch);
    }
}
