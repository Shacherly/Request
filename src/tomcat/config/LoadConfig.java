package tomcat.config;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LoadConfig {

    private static Map<String, String> configCahce = new HashMap<>();

    static {
        try {
            Properties pro = new Properties();
            pro.load(new FileReader("src/server.properties"));
            Enumeration<?> enumeration = pro.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = pro.getProperty(key);
                configCahce.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getPort() {
        return Integer.parseInt(configCahce.get("port"));
    }

    public static String getValue(String key) {
        return configCahce.get(key);
    }
}
