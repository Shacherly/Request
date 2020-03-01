package tomcat;

import tomcat.servlet.HttpServletRequest;
import tomcat.servlet.HttpServletResponse;
import tomcat.servlet.ServletController;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

// 服务器线程管理者
public class ServerHandler extends Thread {
    private Socket socket;
    // private HttpServlet httpServlet = new IndexController();

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        /*
        读取消息  解析  找人做事  响应回去
         */
        acceptRequest();
    }

    private void acceptRequest() {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String content = reader.readLine();
            parseContent(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseContent(String content) {
        // resources?key=value&key=value
        int i = content.indexOf("?");
        String resources = null;

        Map<String, String> paramsMap = new HashMap<>(16);

        // 问号不一定有， 所以一定要判断。
        if (i > -1) {
            resources = content.split("\\?")[0];
            String params = content.split("\\?")[1];
            String[] keyValues = params.split("&");
            for (String s : keyValues) {
                paramsMap.put(s.split("=")[0], s.split("=")[1]);
            }
        } else {
            resources = content;
        }

        // 至此请求发送的字符串解析完毕，但是每次都要单独传递参数显然很麻烦
        // 因此封装HttpServletRequest对象存储参数信息

        HttpServletRequest request = new HttpServletRequest(resources, paramsMap);
        HttpServletResponse response = new HttpServletResponse();

        // 执行请求处理的过程也是填充response对象的过程
        ServletController.findController(request, response);

        resopnse(response);
    }





    // 响应回浏览器
    private void resopnse(HttpServletResponse response) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(response.getResponseContent());
            out.flush();
            String s = "12\"\"3";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
