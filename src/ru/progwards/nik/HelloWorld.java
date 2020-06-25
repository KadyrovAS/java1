package ru.progwards.nik;

public class HelloWorld {
    static void println (String str) {
        System.out.println(str);
    }
    static int addition(int x, int y) {
        return x + y;
    }
    public static void main(String[] args){
        println("Привет, мир!");
        println("Мир, ты слышишь меня?");
        System.out.print("Я знаю, что 5 + 7 = ");
        System.out.println( addition(5,7));
        double y = 5.234 % 2;
        System.out.println(y);
    }
}
