package tomcat.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public String getParameter(String key) {
        return paramsMap.get(key);
    }

}
