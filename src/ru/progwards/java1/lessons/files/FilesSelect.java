package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FilesSelect {
    public void selectFiles(String inFolder, String outFolder, List<String> keys) {

        try {
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.txt");
            Files.walkFileTree(Paths.get(inFolder), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(path))
                        for (String keyWord : isKeyWord(path, keys))
                            createFolder(path, outFolder + "/" + keyWord);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            return;
        }
    }

    public List<String> isKeyWord(Path path, List<String> keys) {
        //Ищет в файле с path ключевые слова из keys
        //Возвращает список из найденных ключевых слов
        List<String> findKeys = new LinkedList<>();
        try {
            String fileReadLine = Files.readString(path);
            for (String lookingForKey: keys)
                if (fileReadLine.contains(lookingForKey))
                    findKeys.add(lookingForKey);
        } catch (IOException e) {
            return findKeys;
        }
        return findKeys;
    }

    public void createFolder(Path path, String outFolder) {
    //Создает директорию outFolder и копирует в нее файл с path
        try {
            Path pathOutFolder = Paths.get(outFolder);
            if (!Files.isDirectory(pathOutFolder)) Files.createDirectories(pathOutFolder);
            Path pathCopy = pathOutFolder.resolve(path.getFileName());
            Files.copy(path, pathCopy, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            //что-то пошло не так
        }
    }

    public static void main(String[] args) {
        Path path = Paths.get("d:");
        FilesSelect fs = new FilesSelect();
        List<String>keys = new LinkedList<>();
        keys.add("Masha");
        keys.add("Ruslan");
        keys.add("cat");
        keys.add("dog");

        fs.selectFiles("d:/school", "d:/school/find", keys);
    }
}
