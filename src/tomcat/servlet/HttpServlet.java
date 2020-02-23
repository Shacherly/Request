package tomcat.servlet;

public abstract class HttpServlet {

    protected abstract void service(HttpServletRequest request, HttpServletResponse response);

}
