package HomeTest;

import java.io.FileReader;
import java.io.IOException;

public class TestJava {
    public static void main(String[] args) {
        double p = 5.42;
        System.out.println(p % 2);
    }


    public Integer sqr(Integer n) throws NullPointerException {
        try {
            return n * n;
        } catch (NullPointerException e) {
            return -1;
        }
    }

    public String test(String filename) throws IOException {
        if (filename == null) throw new IOException("File not found"); else return "File processing";
    }

}