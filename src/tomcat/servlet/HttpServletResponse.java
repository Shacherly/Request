package tomcat.servlet;

import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Data
public class HttpServletResponse {

    private StringBuilder responseContent = new StringBuilder();

    public void write(String msg) {
        responseContent.append(msg);
    }

    public void sendRedirect(String path) {
        File file = new File("src//tomcat//templates//" + path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                responseContent.append(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String getResponseContent() {
        return responseContent.toString();
    }
}
