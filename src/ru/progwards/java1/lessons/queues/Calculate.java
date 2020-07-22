package ru.progwards.java1.lessons.queues;

public class Calculate {
    public static double calculation1() { // 2.2*(3+12.1)
        StackCalc calc = new StackCalc();
        calc.push(2.2);
        calc.push(3);
        calc.push(12.1);
        calc.add();
        calc.mul();
        return calc.get();
    }

    public static double calculation2() { //(737.22+24)/(55.6-12.1)+(19-3.33)*(87+2*(13.001-9.2))
        StackCalc calc = new StackCalc();
        calc.push(737.22);
        calc.push(24);
        calc.add();
        calc.push(55.6);
        calc.push(12.1);
        calc.sub();
        calc.div();

        calc.push(19);
        calc.push(3.33);
        calc.sub();
        calc.push(87);
        calc.push(2);
        calc.push(13.001);
        calc.push(9.2);
        calc.sub();
        calc.mul();
        calc.add();
        calc.mul();
        calc.add();

        return calc.get();
    }

    public static void main(String[] args) {
        System.out.println(calculation2());
    }
}
