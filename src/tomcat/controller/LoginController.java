package tomcat.controller;

import tomcat.servlet.HttpServlet;
import tomcat.servlet.HttpServletRequest;
import tomcat.servlet.HttpServletResponse;

public class LoginController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        System.out.println(name + "----" + pass);
        System.out.println("服务器：【登录成功】");
        response.sendRedirect("login.view");
    }
}
