package ru.progwards.java2.lessons.trees;

import java.util.function.Consumer;

/**
 * Класс реализует сбалансированное двоичное дерево - AvlTree
 * @see BinaryTree
 * @param <K> - Ключ узла
 * @param <V> - Значение узла
 */
public class AvlTree<K extends Comparable<K>, V> {
    private static final String KEYEXIST = "Key already exist";
    private static final String KEYNOTEXIST = "Key not exist";

    /**
     * Конструктор
     * @param root - корень дерева
     */
    public AvlTree(TreeLeaf<K, V> root) {
        this.root = root;
    }

    /**
     * Конструктор по умолчанию
     */
    public AvlTree() {}

    /**
     * Узел дерева {@link TreeLeaf}. Содержит 3 ссылки: parent - родитель, left - корень левого поддерева,
     * right - корень правого поддерева
     * @param <K> - ключ узла
     * @param <V> - значение узла
     */
    class TreeLeaf<K extends Comparable<K>, V> {
        K key;
        V value;
        int height;
        TreeLeaf parent;
        TreeLeaf left;
        TreeLeaf right;

        /**
         * Конструктор узла дерева {@link TreeLeaf}
         * @param key - ключ
         * @param value - значение узла
         */
        public TreeLeaf(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Метод, осуществляющий поиск узла по ключу key
         * @param key
         * @return возвращает найденный узел {@link TreeLeaf}
         */
        private TreeLeaf<K, V> find(K key) {
            int cmp = key.compareTo(this.key);
            if (cmp > 0)
                if (right != null)
                    return right.find(key);
                else
                    return this;
            if (cmp < 0)
                if (left != null)
                    return left.find(key);
                else
                    return this;
            return this;
        }

        /**
         * Добавляет новый узел в дерево {@link AvlTree}
         * @param leaf Новый узел {@link TreeLeaf}
         */
        void put(TreeLeaf<K, V> leaf) {
            int cmp = leaf.key.compareTo(key);
            if (cmp == 0)
                this.value = leaf.value;
            if (cmp > 0) {
                right = leaf;
                leaf.parent = this;
            } else {
                left = leaf;
                leaf.parent = this;
            }
            leaf.height = 1;
        }

        @Override
        /**
         * Переопределение toString()
         * @return Возвращает строку в формате: "key(value)", где key - ключ узла, а value - его значение
         */
        public String toString() {
            return key + "(" + value + ")";
        }

        /**
         * Используется в лямбда-функции для получения всех узлов дерева {@link AvlTree}
         * @param consumer узел дерева {@link TreeLeaf}
         */
        public void process(Consumer<TreeLeaf<K, V>> consumer) {
            if (left != null)
                left.process(consumer);
            consumer.accept(this);
            if (right != null)
                right.process(consumer);
        }
    }

    private TreeLeaf<K, V> root;

    /**
     * Осуществляет поиск узла дерева {@link AvlTree} по ключу (key)
     * @param key - Ключ
     * @return - Значение узла дерева (value)
     */
    public V find(K key) {
        if (root == null)
            return null;
        TreeLeaf found = root.find(key);
        return found.key.compareTo(key) == 0 ? (V) found.value : null;
    }

    /**
     * Добавляет новый узел {@link TreeLeaf} в {@link AvlTree} дерево
     * @param leaf {@link TreeLeaf}
     */
    public void put(TreeLeaf<K, V> leaf)  {
        if (root == null)
            root = leaf;
        else
            root.find(leaf.key).put(leaf);
        calculateHeight(leaf, 1); //пересчет высот узлов
        checkBalance(leaf); //проверка баланса и, при необходимости, вращение с пересчетом высот
    }

    /**
     * В случае, если баланс нарушен, то принимается решение о повороте дерева либо вправо, либо влево
     * @param leaf - узел {@link TreeLeaf} дерева {@link AvlTree}
     */
    private void checkBalance(TreeLeaf<K, V> leaf) {
        TreeLeaf treeLeaf = leaf;
        int bal;
        bal = balance(treeLeaf);
        if (bal == 2)
            rotateLeft(treeLeaf);
        else if (bal == -2)
            rotateRight(treeLeaf);
        if (treeLeaf.parent == null)
            return;
        checkBalance(leaf.parent);
    }

    /**
     * Выполняет расчет высоты для каждого узла дерева {@link AvlTree}
     * @param leaf - узел {@link TreeLeaf}
     * @param height - высота узла {@link TreeLeaf}
     */

    private void calculateHeight(TreeLeaf<K, V> leaf, int height) {
        //пересчет высоты поддеревьев после add от leaf до корневого узла, не включая его
        leaf.height = height;
        if (leaf.parent == null)
            return;
        calculateHeight(leaf.parent, height + 1);
    }

    /**
     * Рассчитывает баланс каждого узла {@link TreeLeaf} дерева {@link AvlTree} как разность между
     * правым поддеревом {@link TreeLeaf} и левым поддеревом {@link TreeLeaf}
     * @param leaf - узел {@link TreeLeaf}
     * @return Баланс
     */
    private int balance(TreeLeaf<K, V> leaf) {
        int left, right;
        left = (leaf.left == null ? 0 : leaf.left.height);
        right = (leaf.right == null ? 0 : leaf.right.height);
        return right - left;
    }

    /**
     * Осуществляет левое вращение (большое или малое)
     * @param leaf - узел {@link TreeLeaf}
     */
    private void rotateLeft(TreeLeaf<K, V> leaf) {
        //Левое вращение
        if (balance(leaf.right) < 0) {
            //Большое левое вращение
            smallRightRotate(leaf.right);
            smallLeftRotate(leaf);
        }
        else {
            // Малое левое вращение
            smallLeftRotate(leaf);
        }
    }

    /**
     * Осуществляет правое вращение (большое или малое)
     * @param leaf - узел {@link TreeLeaf}
     */
    private void rotateRight(TreeLeaf<K, V> leaf) {
        //Правое вращение
        if (balance(leaf.left) > 0) {
            //Большое правое вращение
            smallLeftRotate(leaf.left);
            smallRightRotate(leaf);
        }
        else {
            //Малое правое вращение
            smallRightRotate(leaf);
        }
    }

    private void smallLeftRotate(TreeLeaf<K,V> leaf) {
        //малое левое вращение
        int left = 0;
        int right = 0;
        if (leaf.right.left != null)
            leaf.right.left.parent = leaf;
        leaf.right.parent = leaf.parent;
        if (leaf.parent == null)
            this.root = leaf.right;
        else {
            if (leaf.parent.key.compareTo(leaf.key) > 0)
                leaf.parent.left = leaf.right;
            else
                leaf.parent.right = leaf.right;
        }
        leaf.parent = leaf.right;
        leaf.right = leaf.right.left;
        leaf.parent.left = leaf;
        if (balance(leaf) >= 0) {
            right = (leaf.right == null ? 0 : leaf.right.height);
            leaf.height = right + 1;
        }
        else {
            left = (leaf.left == null ? 0 : leaf.left.height);
            leaf.height = left + 1;
        }
        if (balance(leaf.parent) < 0)
            leaf.parent.height = leaf.parent.left.height + 1;
        else if (balance(leaf.parent) > 0)
            leaf.parent.height = leaf.parent.right.height + 1;
    }

    private void smallRightRotate(TreeLeaf<K,V> leaf) {
        //малое правое вращение
        int left = 0;
        int right = 0;
        if (leaf.left.right != null)
            leaf.left.right.parent = leaf;
        leaf.left.parent = leaf.parent;
        if (leaf.parent == null)
            this.root = leaf.left;
        else {
            if (leaf.parent.key.compareTo(leaf.key) > 0)
                leaf.parent.left = leaf.left;
            else
                leaf.parent.right = leaf.left;
        }
        leaf.parent = leaf.left;
        leaf.left = leaf.left.right;
        leaf.parent.right = leaf;
        if (balance(leaf) >= 0) {
            right = (leaf.right == null ? 0 : right + 1);
            leaf.height = right + 1;
        }
        else {
            left = (leaf.left == null ? 0 : leaf.left.height);
            leaf.height = left + 1;
        }
        if (balance(leaf.parent) < 0)
            leaf.parent.height = leaf.parent.left.height + 1;
        else if (balance(leaf.parent) > 0)
            leaf.parent.height = leaf.parent.right.height + 1;
    }

    /**
     * Добавляет новый узел {@link TreeLeaf} к дереву {@link AvlTree}
     * @param key - ключ
     * @param value - значение
     */
    public void put(K key, V value) {
        put(new TreeLeaf<>(key, value));
    }

    /**
     * Удаляет узел {@link TreeLeaf} с ключем key. После удаления узла осуществляется пересчет высот и балансов всех
     * узлов. При необходимости осуществляется левое или правое вращение вокруг узла, баланс которого был нарушен
     * @param key - ключ
     * @throws TreeException - если узел {@link TreeLeaf} с ключем key не найден
     */
    public void delete(K key) throws TreeException {
        internaldDelete(key);
    }

    public TreeLeaf<K, V> internaldDelete(K key) throws TreeException {
        if (root == null)
            throw new TreeException(KEYNOTEXIST);

        TreeLeaf found = root.find(key);
        TreeLeaf newRoot;
        int cmp = found.key.compareTo(key);
        if (cmp != 0)
            throw new TreeException(KEYNOTEXIST);

        if (balance(found) < 0 && found.left != null) {
            newRoot = findMax(found.left);
            changeConnections(found,newRoot);
            calculateHeightAfterDelete(root);
        }
        else if (balance(found) >= 0 && found.right != null) {
            newRoot = findMin(found.right);
            changeConnections(found, newRoot);
            calculateHeightAfterDelete(root);
        }
        else if (found.parent == null)
            root = null;
        else {
            //удаляем лист
            newRoot = found.parent;
            if (found.parent.right == found)
                found.parent.right = null;
            else
                found.parent.left = null;
            calculateHeightAfterDelete(root);

        }
        if (root != null)
            checkBalanceAfterDelete(root);

        return found;
    }

    /**
     * Осуществляет балансровку дерева {@link AvlTree} после удаления узла {@link TreeLeaf}
     * @param leaf узел {@link TreeLeaf} дерева {@link AvlTree}
     */
    private void checkBalanceAfterDelete(TreeLeaf leaf){
        if (balance(leaf) == 2)
            rotateLeft(leaf);
        else if (balance(leaf) == -2)
            rotateRight(leaf);

        if (leaf.left != null)
            checkBalanceAfterDelete(leaf.left);
        if (leaf.right != null)
            checkBalanceAfterDelete(leaf.right);
    }

    /**
     * Пересчитывет высоты всех узлов {@link TreeLeaf} дерева {@link AvlTree} после удаления узла
     * @param leaf узел {@link TreeLeaf}
     * @return высота узла leaf {@link TreeLeaf}
     */
    private int calculateHeightAfterDelete(TreeLeaf leaf){
        //Пересчет баланса после удаления узла
        int left = 0;
        int right = 0;
        if (leaf.left == null && leaf.right == null) {
            leaf.height = 1;
            return 1;
        }
        if (leaf.left != null)
            left += calculateHeightAfterDelete(leaf.left) + 1;
        if (leaf.right != null)
            right += calculateHeightAfterDelete(leaf.right) + 1;
        leaf.height = (left > right ? left : right);
        return leaf.height;
    }

    /**
     * После удаления узла leafDelete {@link TreeLeaf} все его связи переопределяются для newLeaf {@link TreeLeaf}
     * @param leafDelete Удаленный узел {@link TreeLeaf}
     * @param newLeaf Узел {@link TreeLeaf}, который наследует связи удаленного узла leafDelete {@link TreeLeaf}
     */
    private void changeConnections(TreeLeaf leafDelete, TreeLeaf newLeaf){
        //Создаем копию leaf
        TreeLeaf leaf = new TreeLeaf(newLeaf.key,"");
        leaf.right = newLeaf.right;
        leaf.left = newLeaf.left;
        leaf.parent = newLeaf.parent;

        if (leafDelete.right != newLeaf)
            newLeaf.right =  leafDelete.right;
        if (leafDelete.left != newLeaf)
            newLeaf.left = leafDelete.left;

        if (newLeaf.parent.right == newLeaf)
            newLeaf.parent.right = leaf.left;
        else
            newLeaf.parent.left = leaf.right;

        newLeaf.parent = leafDelete.parent;

        if (newLeaf.left != null)
            newLeaf.left.parent = newLeaf;
        if (newLeaf.right != null)
            newLeaf.right.parent = newLeaf;

        if (newLeaf.parent != null) {
            if (newLeaf.parent.right == leafDelete)
                newLeaf.parent.right = newLeaf;
            else
                newLeaf.parent.left = newLeaf;
        }
        if (newLeaf.parent == null)
            this.root = newLeaf;
    }

    /**
     * Находит узел в поддереве leaf {@link TreeLeaf} с самым маленьким ключем key
     * @param leaf узел {@link TreeLeaf}
     * @return узел {@link TreeLeaf}
     */
    private TreeLeaf<K,V> findMin(TreeLeaf<K,V> leaf) {
        if (leaf.left == null)
            return leaf;
        return findMin(leaf.left);
    }

    /**
     * Находит узел в поддереве leaf {@link TreeLeaf} с самым большим ключем key
     * @param leaf
     * @return
     */
    private TreeLeaf<K,V> findMax(TreeLeaf<K,V> leaf) {
        if (leaf.right == null)
            return leaf;
        return findMax(leaf.right);
    }

    /**
     * Находит узел {@link TreeLeaf} с ключем равным oldKey и меняет в этом узле ключ на newKey
     * @param oldKey ключ узла {@link TreeLeaf}, подлежащий замене
     * @param newKey новый ключ узла {@link TreeLeaf}
     * @throws TreeException если узел {@link TreeLeaf} с ключем oldKey найден не был
     */
    public void change(K oldKey, K newKey) throws TreeException {
        TreeLeaf<K, V> current = internaldDelete(oldKey);
        current.key = newKey;
        current.left = null;
        current.right = null;
        put(current);
    }

    /**
     * Используется лямбда функцией для получения всех узлов {@link TreeLeaf} дерева {@link AvlTree}
     * @param consumer узел {@link TreeLeaf}
     */
    public void process(Consumer<TreeLeaf<K, V>> consumer) {
        if (root != null)
            root.process(consumer);
    }
}

