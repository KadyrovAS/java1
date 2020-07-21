package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ProductAnalytics {
//Я не нарушал установленную в ТЗ сигнатуру, но думаю было бы правильнее работать с Set, а не List

    private List<Shop> shops; //список магазинов
    private List<Product> products; //список всех имеющихся в ассортименте товаров.
    public ProductAnalytics(List<Product> products, List<Shop> shops) {
        this.products = products;
        this.shops = shops;
    }

    public Set<Product> existInAll() { //товары из products, которые имеются во всех магазинах
        int countShops = this.shops.size(); //Количество всех магазинов
        Set<Product> setProduct = new HashSet<Product>();
        for (Product currentProduct: this.products)
            if (hasProducts(currentProduct) == countShops) setProduct.add(currentProduct);
        return setProduct;
    }

    public Set<Product> existAtListInOne() { //товары из products, которые имеются хотя бы в одном магазине
        Set<Product> setProduct = new HashSet<Product>();
        for (Product currentProduct: this.products)
            if (hasProducts(currentProduct) > 0) setProduct.add(currentProduct);
        return setProduct;
    }

    public Set<Product> notExistInShops() { //товары из products, которых нет ни в одном магазине
        Set<Product> setProduct = new HashSet<Product>();
        for (Product currentProduct: this.products)
            if (hasProducts(currentProduct) == 0) setProduct.add(currentProduct);
        return setProduct;
    }
    public Set<Product> existOnlyInOne() { //товары из products, которые есть только в одном магазине
        Set<Product> setProduct = new HashSet<Product>();
        for (Product currentProduct: this.products)
            if (hasProducts(currentProduct) == 1) setProduct.add(currentProduct);
        return setProduct;
    }

    public int hasProducts(Product product) { //возвращает количество магазинов, в которых есть данный товар
        int count = 0;
        for (Shop currentShop: shops)
            if(currentShop.getProducts().contains(product)) count ++;
        return count;
    }
}
