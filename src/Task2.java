public class Task2 {
    public static void printJava(){
        String str1 = "Хорошо идут дела";
        String str2 = "Изучаю Java я!";
        String str3 = " ";
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str1+str3+str2);
        System.out.println(str2+str3+str1);
    }
    public static int subtraction(int x, int y){
        System.out.print("Вызвана функция subtraction() с параметрами ");
        System.out.println("x = "+x+", y = "+y);
        return x-y;
    }
    public static int addition(int x, int y){
        System.out.print("Вызвана функция addition() с параметрами ");
        System.out.println("x = "+x+", y = "+y);
        return x+y;
    }
    public static int multiplication(int x, int y){
        System.out.print("Вызвана функция multiplication() с параметрами ");
        System.out.println("x = "+x+", y = "+y);
        return x*y;
    }
    public static void calculation(){
        int a = 34;
        int b = 55;
        int c;
        System.out.println("a = "+a);
        System.out.println("b = "+b);
        c=addition(a,b);
        System.out.println("a + b = " + c);
        c=subtraction(a,b);
        System.out.println("a - b = " + c);
        c=multiplication(a,b);
        System.out.println("a * b = " + c);
    }
    public static void main(String[] args){
        int n;
        printJava();
        n=subtraction(45,12);
        n=subtraction(23,55);
        n=addition(128,787);
        n=addition(528,387);
        n=multiplication(124, 87);
        n=multiplication(1528,3);
    }
}
