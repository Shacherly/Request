package test;

import java.io.*;

public class TestMain {
    public static void main(String[] args) {
        File file = new File("src//test//index.txt");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
    }
}
