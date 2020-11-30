package ru.progwards.java2.lessons.reflection;

import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.*;

public class ClassInspector {
    public static void inspect(String clazz) throws ClassNotFoundException, IOException {
        Class insClass = Class.forName(clazz);
        //Необходимо указать path
        String path = "d:/java/" + insClass.getSimpleName() + ".java";
        Path pathFile = Paths.get(path);
        Files.writeString(pathFile, "class " + insClass.getSimpleName() + " {" + "\n");

        String line = "";

        //Определяем поля класса
        Field[] fields = insClass.getDeclaredFields();
        for (Field field: fields) {
            line = "    " + Modifier.toString(field.getModifiers()) + " " +
            parseType(field.getGenericType().toString()) + " " + field.getName();
            Files.writeString(pathFile, line + "\n", StandardOpenOption.APPEND);
        }

        //Определяем конструкторы
        Constructor[] constructors = insClass.getDeclaredConstructors();
        for (Constructor constructor: constructors) {
            line = "    " + Modifier.toString(constructor.getModifiers()) + " " +
                    parseType(constructor.getName()) + "(";
            Parameter[] parameters = constructor.getParameters();

            for (int i = 0; i < constructor.getParameterCount(); i ++){
                if (i > 0) line += ", ";
                line += parseType(parameters[i].getType().toString()) + " ";
                line += parameters[i].getName();
            }
            line += ") {}";
            Files.writeString(pathFile, line + "\n", StandardOpenOption.APPEND);
        }

        //Определяем методы
        Method[] methods = insClass.getDeclaredMethods();
        for (Method method: methods) {
            line = "    " + Modifier.toString(method.getModifiers()) + " " +
                    parseType(method.getReturnType().toString()) +
                    parseType(method.getName()) + "(";
            Parameter[] parameters = method.getParameters();

            for (int i = 0; i < method.getParameterCount(); i ++){
                if (i > 0) line += ", ";
                line += parseType(parameters[i].getType().toString()) + " ";
                line += parameters[i].getName();
            }
            line += ") {}";
            Files.writeString(pathFile, line + "\n", StandardOpenOption.APPEND);
        }

        Files.writeString(pathFile,"}",StandardOpenOption.APPEND);
    }

    public static String parseType(String type) {
        String line = "";
        String[] words = type.toString().split(" ");
        for (String word: words)
            if (word.compareTo("class") != 0)
                line += word + " ";
        words = line.split("\\.");
        line = words[words.length-1];
        if (line.contains("$"))
            line = line.replace("$",".");
        return line.trim();
    }
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        inspect("ru.progwards.java2.lessons.reflection.Employee");
    }
}
