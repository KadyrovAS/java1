package ru.progwards.java2.lessons.trees;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
// Для пересчета и проверки баланса, c последующими вращениями при необходимости я использовал рекурсивный подход
// После удаления узла я пересчитываю в рекурсии все дерево, т.к. удаление узла может инициировать вращение, которое
// может изменить баланс во всем дереве. Понимаю, что это не всегда рационально
// Рекурсивная реализация привела к тому, что при удалении при больших размерах дерева возникает переполнение стека
// Пока я отлаживал программу на небольших размерах, этой проблемы не возникало
// Сейчас бы я постарался от рекурсии отказаться

public class AvlTest {
    static final int ITERATIONS = 100_000;
    public static void main(String[] args) throws TreeException{

        TreeMap<Integer, String> map = new TreeMap<>();
        AvlTree<Integer, String> tree = new AvlTree<>();

        long start, finis;
        int key;

        //Вставка случайных чисел
        System.out.println("Тест на операцию: вставка случайных чисел");
        start = System.currentTimeMillis();

        for(int i=0; i<ITERATIONS; i++) {
            key = ThreadLocalRandom.current().nextInt();
            if (!map.containsKey(key)) {
                tree.put(key, "key=" + key);
            }
        }

        finis = System.currentTimeMillis();
        System.out.println("AvlTree: " + (finis - start) );

        start = System.currentTimeMillis();

        for(int i=0; i<ITERATIONS; i++) {
            key = ThreadLocalRandom.current().nextInt();
            if (!map.containsKey(key)) {
                map.put(key, "key=" + key);
            }
        }

        finis = System.currentTimeMillis();
        System.out.println("TreeMap: " + (finis - start) );

        System.out.println("------------------------");
        System.out.println("Тест на операцию: поиск:");

        ArrayList<AvlTree.TreeLeaf> sorted = new ArrayList<>();
        tree.process(sorted::add);

        start = System.currentTimeMillis();
        for (AvlTree.TreeLeaf leaf: sorted)
            tree.find((int) leaf.key);
        finis = System.currentTimeMillis();
        System.out.println("AvlTree: " + (finis - start));

        start = System.currentTimeMillis();
        for (AvlTree.TreeLeaf leaf: sorted)
            map.get(leaf.key);
        finis = System.currentTimeMillis();
        System.out.println("TreeMap: " + (finis - start));

        System.out.println("------------------------");
        System.out.println("Тест на операцию удаления");

//        start = System.currentTimeMillis();
//        for (AvlTree.TreeLeaf leaf: sorted)
//            tree.delete((int) leaf.key);
//        finis = System.currentTimeMillis();
//        System.out.println("AvlTree: " + (finis - start));

        start = System.currentTimeMillis();
        for (AvlTree.TreeLeaf leaf: sorted)
            map.remove(leaf.key);
        finis = System.currentTimeMillis();
        System.out.println("TreeMap: " + (finis - start));
    }
}
