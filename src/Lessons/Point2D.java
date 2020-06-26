package Lessons;
/*
Создайте классы Point2D, Point3D, в одном окошке, последовательно один, за другим

Класс Point2D - точка с 2-мя координатами, содержит

переменные int x; int y;
конструктор с параметрами int x, int y, устанавливающий соответствующие переменные
переопределение функции toString(), которая должна возвращать результат в формате х,у, например 23,12
Класс Point3D - точка с 3-мя координатами

наследуется от Point2D
содержит приватную переменную int z;
конструктор с параметрами int x, int y, int z, вызывающий конструктор родителя и устанавливающий соответствующую свою переменную
переопределение функции toString(), которая должна возвращать результат в формате х,у,z, например 23,12,37 для этого вызывать функцию toString() родителя внутри себя


 */
public class Point2D {
    int x;
    int y;
    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}

public class Point3D extends Point2D {
    private int z;
    public Point3D (int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    @Override
    public String toString() {
        return x +"," + y + "," + z;
    }
}

    public static void main(String[] args) {
        Point2D p2d = new Point2D(3,4);
        Point3D p3d = new Point3D(3,4,5);
        System.out.println(p2d);
        System.out.println(p3d);
    }