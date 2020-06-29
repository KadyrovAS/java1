package ru.progwards.java1.lessons.bitsworld;

public class Binary {
    byte num;
    public Binary(byte num) {
        this.num = num;
    }
    public String toString(){
        String result = "";
        byte num = this.num;
        int sym = 0;

        for (int i = 1; i <= 8; i ++) {
            sym = num & 0b00000000_00000000_00000000_00000001;
            result = Integer.toString(sym) + result;
            num >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        byte num = 4;
        Binary numeric = new Binary(num);
        System.out.println(numeric.toString());
    }
}
