package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FindDuplicates {
    List <List<String>> listResult = new ArrayList<>();
    public List<List<String>> findDuplicates(String startPath) {
        List <Path> listPath = new ArrayList<Path>();
        try {
            Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    listPath.add(path);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            return null;
        }

        int resultCount = 0;
        for (int i = 0; i < listPath.size() - 1; i ++)
            for (int k = i + 1; k < listPath.size(); k ++)
                 if(identically(listPath.get(i), listPath.get(k)))
                     addPath(listPath.get(i), listPath.get(k));

    return listResult;
    }

    public boolean identically(Path path1, Path path2) {
        //метод сравнивает имена файлов, их атрибуты и содержимое по path1 и path2
        //в случае идентичности возвращает true

        try { //Переделать через список атрибутов

            if (Files.isDirectory(path1) || Files.isDirectory(path2)) return false;
            if (path1.getFileName().compareTo(path2.getFileName()) != 0) return false;
            if (Files.getLastModifiedTime(path1).compareTo(Files.getLastModifiedTime(path2)) != 0)
                return false;
            if (Files.size(path1) != Files.size(path2)) return false;
            if (Files.readString(path1).compareTo(Files.readString(path2)) != 0) return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void addPath(Path path1, Path path2) {
        //метод проверяет наличие path в списке listResult
        List <String> currentList;
        Path currentPath;
        boolean isSame, isSame1, isSame2;
        try {
            isSame = false;
            for (int i = 0; i < listResult.size(); i++) {
                currentList = listResult.get(i);
                currentPath = Paths.get(currentList.get(0));
                if (currentPath.getFileName().compareTo(path1.getFileName()) == 0) {
                    isSame = true; //В listResult есть файл с искомым именем getFileName
                    isSame1 = false; //В currentList path совпадает c path1
                    isSame2 = false; //В currentList path совпадает c path2
                    for (int k = 0; k < currentList.size(); k++) {
                        currentPath = Paths.get(currentList.get(k));
                        if (Files.isSameFile(currentPath, path1)) isSame1 = true;
                        if (Files.isSameFile(currentPath, path2)) isSame2 = true;
                    }
                    if (!isSame1) currentList.add(path1.toString());
                    if (!isSame2) currentList.add(path2.toString());
                    listResult.set(i, currentList);
                    return;
                }
            }
            if (!isSame) {
                currentList = new ArrayList<String>();
                currentList.add(path1.toString());
                currentList.add(path2.toString());
                listResult.add(currentList);
            }
        } catch (IOException e) {
            return;
        }
        return;
    }

    public static void main(String[] args) throws IOException {
        List <List<String>> listResult;
        FindDuplicates exemple = new FindDuplicates();
        listResult = exemple.findDuplicates("d:/School");
        for (List<String> item: listResult) {
            for (String Line: item) System.out.println(Line);
            System.out.println("------------------------------------------");
        }
    }
}
