package ru.progwards.java2.lessons.classloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZonedDateTime;

import java.time.format.DateTimeFormatter;

import java.util.LinkedHashMap;
import java.util.Map;

public class PathLoader extends ClassLoader {
    final static String PATH_OF_TASKS = "C:/data/";
    final static String DOT_CLASS = ".class";
    private static PathLoader loader = new PathLoader(PATH_OF_TASKS);
    private final String basePath;

    public PathLoader(String basePath) {
        this(basePath, ClassLoader.getSystemClassLoader());
    }

    public PathLoader(String basePath, ClassLoader parent) {
        super(parent);
        this.basePath = basePath;
    }

    static class Tsk {
        int date; //Дата создания
        Target target; //код

        public Tsk(int date, Target target) {
            this.date = date;
            this.target = target;
        }
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            String classPath = className.replace(".", "/");
            Path classPathName = Paths.get(basePath + classPath + DOT_CLASS);
            if (Files.exists(classPathName)) {
                byte b[] = Files.readAllBytes(classPathName);
//                int position = className.indexOf(".") + 1;
//                className = className.substring(position);
                return defineClass(className, b, 0, b.length);
            } else {
                return findSystemClass(className);
            }
        } catch (IOException ex) {
            throw new ClassNotFoundException(className);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = findClass(name);
        if (resolve)
            resolveClass(c);
        return c;
    }

    private static void updateTaskList(Map<String, Tsk> tasks)
            throws IOException {
        Path logPath = Paths.get(PATH_OF_TASKS + "patchloader.log");
        if (!Files.exists(logPath))
            Files.writeString(logPath, "");
        Files.walkFileTree(Paths.get(PATH_OF_TASKS), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if (path.toString().endsWith(DOT_CLASS)) {
                    String className = makeClassName(path);
                    Tsk tsk = tasks.get(className);
                    if (tsk == null || tsk.date < dateFromPath(path)) {
                        try {
                            Class taskClass = loader.loadClass(className, true);
                            Object obj = taskClass.getDeclaredConstructor().newInstance();
                            Target newTask = (Target) obj;
                            tasks.put(className, new Tsk(dateFromPath(path), newTask));
                            String ln = ZonedDateTime.now().format(
                                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss")) +
                                    " " + className + " загружен из " + path + " успешно";
                            Files.writeString(logPath, ln + "\n", StandardOpenOption.APPEND);
                        } catch (ClassNotFoundException |
                                IllegalAccessException |
                                InstantiationException |
                                NoSuchMethodException |
                                InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException e) {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static int dateFromPath(Path path) throws IOException {
        path = path.toAbsolutePath().toRealPath();
        Path relPath = Paths.get(PATH_OF_TASKS).relativize(path);
        String className = relPath.toString().replaceAll("[\\/\\\\]", ".");
        if (className.toLowerCase().endsWith(DOT_CLASS))
            className = className.substring(0, className.length() - DOT_CLASS.length());

        int position = className.indexOf(".");
        className = className.substring(0, position);
        return Integer.valueOf(className);
    }

    private static String makeClassName(Path path) throws IOException {
        path = path.toAbsolutePath().toRealPath();
        Path relPath = Paths.get(PATH_OF_TASKS).relativize(path);
        String className = relPath.toString().replaceAll("[\\/\\\\]", ".");
        if (className.toLowerCase().endsWith(DOT_CLASS))
            className = className.substring(0, className.length() - DOT_CLASS.length());

        int position = className.indexOf(".") + 1;
        className = className.substring(position);

        return className;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Tsk> tasks = new LinkedHashMap<>();
        while (true) {
            updateTaskList(tasks);
            Thread.sleep(5_000);
        }
    }
}