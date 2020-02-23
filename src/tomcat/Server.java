package tomcat;

import tomcat.config.LoadConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        initServer();
    }

    public static void initServer() {
        System.out.println("启动服务器");
        try {
            ServerSocket server = new ServerSocket(LoadConfig.getPort());
            // 如果成功接收到请求则返回一个Socket对象
            while (true) {
                Socket socket = server.accept();
                ServerHandler serverHandler = new ServerHandler(socket);
                serverHandler.start();

            }

            // PrintWriter in = new PrintWriter(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
