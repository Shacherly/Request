package tomcat.servlet;

import anexception.ResourcesNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

// 管理找控制器方法的类
public class ServletController {

    // 增加缓存，每个请求的资源都对应一个控制器啊
    private static HashMap<String, String> controllerCache = new HashMap<>();

    private static HashMap<String, HttpServlet> httpServletCache = new HashMap<>();

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/web.properties"));
            Enumeration<?> requestNames = properties.propertyNames();
            while (requestNames.hasMoreElements()) {
                String resources = (String) requestNames.nextElement();
                String controller = properties.getProperty(resources);
                controllerCache.put(resources, controller);
            }
        } catch (IOException e) {
            e.printStackTrace();


        }
    }


    // 找人干活    控制层
    public static void findController(HttpServletRequest request, HttpServletResponse response) {
        String resources = request.getResources();
        String controller = null;
        try {

            // 先去控制器缓存中寻找控制器
            HttpServlet deriveServlet = httpServletCache.get(resources);

            // 为空说明缓存中没有，增加缓存！
            if (deriveServlet == null) {
                controller = controllerCache.get(resources);
                // 严谨性判断，如果资源请求对应的控制器也不存在，就要处理。
                if (controller == null) {
                    throw new ResourcesNotFoundException("请求的 " + resources + " 不存在！");
                }
                Class<HttpServlet> aClass = (Class<HttpServlet>) Class.forName(controller);
                deriveServlet = aClass.newInstance();
                httpServletCache.put(resources, deriveServlet);
            }
            // =========以上可以确保HttpServlet对象肯定存在了

            // deriveServlet肯定不为空，上面为空下面会获取！
            Method service = deriveServlet.getClass().getDeclaredMethod("service", HttpServletRequest.class, HttpServletResponse.class);
            service.setAccessible(true);
            service.invoke(deriveServlet, request, response);
        } catch (ResourcesNotFoundException e) {
            response.write(e.getMessage());
        } catch (NoSuchMethodException e) {
            response.write("405 没有可执行的方法！");
        } catch (ClassNotFoundException e) {
            response.write("请求的 " + controller + " 不存在！");
        } catch (Exception e) {
            // 大异常要放下面！！！
            // 打印错误，服务器自己断掉！！！
            e.printStackTrace();
        }
    }

}
