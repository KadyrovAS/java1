package ru.progwards.java2.lessons.recursion;

public class AsNumbersSum {

    static int numberSum;
    public static String asNumbersSum(int number) {
        numberSum = number;
        String res = String.valueOf(number) + numbersSum(number);
        return res;
    }
    public static String numbersSum(int number) {
        if (number <= 1) return "";
        String res = nums(number, number - 1) + numbersSum(number - 1);
        return res;
    }
    public static String nums(int number, int num) {
        if (number <= 1) return "";
        if ((number - num) > num) return "";
        String res = " = " + String.valueOf(num) + "+" + String.valueOf(number - num);
        if (number != numberSum)
            res += plusOne(numberSum - number);
        res += nums(number, --num);
        return res;
    }

    public static String plusOne(int num){
        if (num < 1) return "";
        String res = "+1" + plusOne(num - 1);
        return res;
    }

    public static void main(String[] args) {
        System.out.println(asNumbersSum(6));
    }
}
