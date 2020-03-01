package tomcat.servlet;

public interface RequestDispatcher {

    void forward(HttpServletRequest request, HttpServletResponse response);

}
