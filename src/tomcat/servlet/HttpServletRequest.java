package tomcat.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tomcat.servlet.impl.DispatcherImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpServletRequest {

    /**
     * 请求内容content包含resources和params
     */

    private String resources;
    private Map<String, String> paramsMap;

    public HttpServletRequest(String resources, Map<String, String> paramsMap) {
        this.resources = resources;
        this.paramsMap = paramsMap;
    }

    // ===========================
    private StringBuilder forwardContent = new StringBuilder();
    private Map<String, String> forwardMap = new LinkedHashMap<>();

    private Attribute attribute = new Attribute();

    private RequestDispatcher requestDispatcher = new DispatcherImpl();
    // ============================

    public String getParameter(String key) {
        return paramsMap.get(key);
    }

    // ==============================
    public RequestDispatcher getRequestDispatcher(String path) {
        forwardMap.put("forwardPath", path);
        File file = new File("src//tomcat//templates//" + path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                forwardContent.append(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestDispatcher;
    }


    public Object getAttribute(String key) {
        return attribute.getView().get(key);
    }

    public void setAttribute(String key, Object value) {
        this.attribute.getView().put(key, value);
    }
    // ==================================
}
