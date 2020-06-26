package ru.progwards.java1.lessons.compare_if_cycles;

public class CyclesGoldenFibo {
    public static boolean containsDigit(int number, int digit) {
        //Возвращает true, если число number содержит цифру digi
        String ln1 = Integer.toString(number);
        String ln2 = Integer.toString(digit);
        return ln1.contains(ln2);
    }
    public static int fiboNumber(int n) {
        int fb1 = 1;
        int fb2 = 1;
        int fb3 = 1;
        if (n < 3) return 1;
        else {
            for (int i = 3; i <= n; i++) {
                fb3 = fb1 + fb2;
                fb1 = fb2;
                fb2 = fb3;
            }
        }
        return fb2;
    }

    public static boolean isGoldenTriangle(int a, int b, int c) {
        /*
        Возвращает true, если треугольник со сторонами a, b, c является Золотым.
        Определим критерии. Он должен быть равнобедренным и отношение ребра к основанию должно лежать
         между значениями 1.61703 и 1.61903.
         */
        int r; //ребро равнобедренного треугольника
        int osn; //основание равнобедренного треугольника
        double fib_min = 1.61703;
        double fib_max = 1.61903;

        if (TriangleInfo.isIsoscelesTriangle(a,b,c)) { //Треугольник равнобедренный
            if (a == b) {
                r = a;
                osn = c;
            } else if (a == c) {
                r = a;
                osn = b;
            } else {
                r = b;
                osn = a;
            }
            //Отношение ребра к основанию соответствует числу Фи
            if ((double) r / (double) osn >= fib_min && (double) r / (double) osn <= fib_max) return true;
            else return false;
        }
        else return false;

    }
    public static void main(String[] args) {
        int a; //ребро
        int b; //основание

        for (int i=1; i<=15; i++) {
            System.out.println(fiboNumber(i));
        }
        int i=3;
        while (fiboNumber(i)<100){
            a = fiboNumber(i-1);
            b = fiboNumber(i);
            if (isGoldenTriangle(a, b, b)) {
                System.out.println(a + " " + b);
            }
            i++;
        }
    }
}
