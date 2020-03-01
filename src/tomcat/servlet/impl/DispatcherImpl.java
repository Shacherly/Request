package tomcat.servlet.impl;

import tomcat.servlet.HttpServletRequest;
import tomcat.servlet.HttpServletResponse;
import tomcat.servlet.RequestDispatcher;

public class DispatcherImpl implements RequestDispatcher {
    @Override
    public void forward(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder forwardContent = request.getForwardContent();
        int lt = forwardContent.indexOf("<%");
        int gt = forwardContent.indexOf("%>");
        String substring = forwardContent.substring(lt + 2, gt);
        String forwardPath = request.getForwardMap().get("forwardPath");
        StringBuilder msg = forwardContent.replace(lt + 2, gt, request.getAttribute(substring).toString());

        response.write(msg.toString());
    }
}
