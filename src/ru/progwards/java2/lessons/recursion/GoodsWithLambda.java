package ru.progwards.java2.lessons.recursion;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GoodsWithLambda {
    public List<Goods>list;
    public void setGoods(List<Goods> list){
        this.list = list;
    }
    public List<Goods> sortByName(){
        //вернуть список, отсортированный по наименованию
        list.sort(Comparator.comparing(a->a.name));
        return list;
        }
    public List<Goods> sortByNumber(){
        //вернуть список, отсортированный по артикулу, без учета регистра
        list.sort(Comparator.comparing(a->a.number.toUpperCase()));
        return list;
    }
    public List<Goods> sortByPartNumber(){
        //вернуть список, отсортированный по первым 3-м символам артикула, без учета регистра
        list.sort(Comparator.comparing(a->a.number.toUpperCase().substring(0,4)));
        return list;
    }
    public List<Goods> sortByAvailabilityAndNumber(){
        //вернуть список, отсортированный по количеству, а для одинакового количества, по артикулу, без учета регистра
        list.sort((a,b)->a.available == b.available ?
                        a.number.toUpperCase().compareTo(b.number.toUpperCase()):
                        Integer.compare(a.available, b.available));
        return list;
    }
    public List<Goods> expiredAfter(Instant date){
        //вернуть список, с товаром, который будет просрочен после указанной даты, отсортированный по дате годности
        return list.stream().filter(a->a.expired.compareTo(date)<0).collect(Collectors.toList());
    }
    public List<Goods> сountLess(int count){
    //вернуть список, с товаром, количество на складе которого меньше указанного, отсортированный по количеству
        return list.stream().filter(a->a.available < count).collect(Collectors.toList());
}
    public List<Goods> сountBetween(int count1, int count2){
    //вернуть список, с товаром, количество на складе которого больше count1 и меньше count2, отсортированный по количеству
        Predicate<Goods> compare1 = a->a.available > count1;
        Predicate<Goods> compare2 = a->a.available < count2;
        return list.stream().filter(compare1.and(compare2)).collect(Collectors.toList());
    }
}
