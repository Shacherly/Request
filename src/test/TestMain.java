package test;

import java.io.*;

public class TestMain {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/test/index.txt");
        FileReader fileReader = new FileReader("src/test/index.txt");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            BufferedReader reader1 = new BufferedReader(fileReader);
            String contnet = "";
            StringBuilder builder = new StringBuilder(contnet);
            while ((contnet = reader.readLine()) != null) {
                builder.append(contnet);
            }
            System.out.println(builder);
            String content1 = null;
            char[] chars = new char[1024];
            while ((fileReader.read(chars)) != -1) {
                String res = new String(chars);
                System.out.print(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
    }
}
