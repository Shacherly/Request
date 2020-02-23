package tomcat.controller;

import tomcat.servlet.HttpServlet;
import tomcat.servlet.HttpServletRequest;
import tomcat.servlet.HttpServletResponse;

public class IndexController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("找到资源：" + request.getResources());
        System.out.println("找到参数：" + request.getParamsMap());

        // response.write("响应信息咯=====!!!!!!!1");
        response.sendRedirect("index.view");
    }
}
