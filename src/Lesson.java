public class Lesson {
    static long factorial(long n) {
        long r = 1;
        for (long i=1;i<=n;i++) r *= i;
        return r;
    }
    public static void main(String[] args) {

        System.out.println(factorial(0));
    }
}
