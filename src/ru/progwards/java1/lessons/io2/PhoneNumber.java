package ru.progwards.java1.lessons.io2;

public class PhoneNumber {
    public static String format(String phone) {
        String phoneResult = "";
        for (char sim: phone.toCharArray()) {
            if(Character.isDigit(sim)) phoneResult += sim;
        }
        if (phoneResult.length() == 10)
            phoneResult = "+7" + phoneResult;
        else if (phoneResult.length() == 11)
            phoneResult = "+7" + phoneResult.substring(1);
        else throw new RuntimeException("Неверный формат номера");

        return phoneResult.substring(0,2) + "(" + phoneResult.substring(2,5) + ")" +
                phoneResult.substring(5,8) + "-" + phoneResult.substring(8);
    }

    public static void main(String[] args) {
        System.out.println(format("79991112233"));
        System.out.println(format("8(999)111-22-33"));
        System.out.println(format("8 999 111 22 33"));
//        System.out.println(format("8 999 111 22 33 99"));
    }
}
