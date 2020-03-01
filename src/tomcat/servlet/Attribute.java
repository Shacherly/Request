package tomcat.servlet;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Attribute {

    private Map<String, Object> view = new LinkedHashMap<>();

    // public
}
