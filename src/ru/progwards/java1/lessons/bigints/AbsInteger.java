package ru.progwards.java1.lessons.bigints;
//Чувствую, что цель задачи реализовать либо анонимный класс, либо локальный.
//Но в тз речь идет только о методах, да и функция только одна - на сложение.
//И я не понял, зачем еще какие то методы нужны в классах?
//Наверное опять задачу не понял? Сергей, уточните пожалуйста, если вдруг что то не так


public class AbsInteger {
    public int value;

    AbsInteger(int value) {this.value = value;}

    static AbsInteger add(AbsInteger num1, AbsInteger num2) {
        int sum;
        sum = num1.value + num2.value;
        if (sum >= -128 && sum <= 127)
            return new ByteInteger((byte) sum);
        else if (sum >= -32768 && sum <= 32767)
            return new ShortInteger((short) sum);
        else return new IntInteger(sum);
    }

    @Override
    public String toString() {
        return "AbsInteger{" +
                "value=" + value +
                '}';
    }

    public static class ByteInteger extends AbsInteger {
        public byte value;
        public ByteInteger(byte value) {super(value); this.value = (byte) value;}

        @Override
        public String toString() {
            return "ByteInteger{" +
                    "value=" + value +
                    '}';
        }


    }

    public static class ShortInteger extends AbsInteger {
        public short value;
        public ShortInteger(short value) {super(value); this.value = (short) value;}

        @Override
        public String toString() {
            return "ShortInteger{" +
                    "value=" + value +
                    '}';
        }

    }

    public static class IntInteger extends AbsInteger {
        public int value;
        public IntInteger(int value) {super(value); this.value = (int) value;}

        @Override
        public String toString() {
            return "IntInteger{" +
                    "value=" + value +
                    '}';
        }

    }

    public static void main(String[] args) {
        ByteInteger bi;
        IntInteger ii;
        byte bt1 = 122;
        int bt2 = 10;

        bi = new ByteInteger(bt1);
        ii = new IntInteger(bt2);

        System.out.println(bi.toString());
        System.out.println(ii.toString());
        System.out.println();

        System.out.println(AbsInteger.add(bi, ii));
    }
}