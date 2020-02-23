package browser;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Browser {
    private Scanner input = new Scanner(System.in);
    private Socket socket;
    private String ip;
    private int port;

    public static void main(String[] args) {
        new Browser().openBrower();
    }

    public void openBrower() {
        System.out.println("打开浏览器！");
        System.out.print("URL:");
        String url = input.nextLine();
        parseURL(url);
    }


    private void parseURL(String url) {
        // ip:port/资源?k=v&k=v
        int colonIndex = url.indexOf(":");
        int slashIndex = url.indexOf("/");
        ip = url.split(":")[0];
        port = Integer.parseInt(url.substring(colonIndex + 1, slashIndex));
        String content = url.substring(slashIndex + 1);
        String params = null;
        if (url.contains("?")) {
            params = url.split("\\?")[1];
            System.out.println("Browser【parseURL】方法解析到请求参数" + params);
        }
        send(ip, port, content);
    }

    private void send(String ip, int port, String content) {
        try {
            socket = new Socket(ip, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream);
            // 发送出去
            out.println(content);
            out.flush();
            // 等待响应信息

            receiveResponse();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveResponse() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String content = reader.readLine();
            // 解析响应信息并展示
            createView(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 解析响应信息并展示
    private void createView(String content) {
        String formAction = null;
        Map<String, String> params = null;

        content = content.replace("<br>", "\r\n");
        while (true) {

            // 浏览器  解析html  进行展示
            int ltIdx = content.indexOf("<");
            int gtIdx = content.indexOf(">");
            if (ltIdx != -1 && gtIdx != -1 && ltIdx < gtIdx) {
                System.out.print(content.substring(0, ltIdx));
                // <input name="name" value="">
                String tag = content.substring(ltIdx, gtIdx + 1);
                if (tag.contains("form")) {
                    String[] kVs = tag.split(" ");
                    for (String kV : kVs) {
                        if (kV.contains("=")) {
                            String[] k_v = kV.split("=");
                            if ("action".equals(k_v[0])) {
                                formAction = k_v[1].substring(1, k_v[1].length() - 1);
                            }
                        }
                    }
                } else if (tag.contains("input")) {
                    String value = input.nextLine();
                    if (params == null) {
                        params = new LinkedHashMap<>();
                    }
                    String[] kVs = tag.split(" ");
                    for (String kV : kVs) {
                        if (kV.contains("=")) {
                            String[] k_v = kV.split("=");
                            if ("name".equals(k_v[0])) {
                                String key = k_v[1].substring(1, k_v[1].length() - 1);
                                params.put(key, value);
                            }
                        }
                    }
                }

                // 解析完第一对标签，截取后面的给下一次解析
                content = content.substring(gtIdx + 1);
            } else {
                System.out.println(content);
                break;
            }
        }
        // 解析完毕
        if (formAction != null) {
            requestAgain(formAction, params);
        }
    }

    private void requestAgain(String formAction, Map<String, String> params) {
        StringBuilder url = new StringBuilder(ip);
        url.append(":").append(port).append("/").append(formAction);
        if (params != null) {
            url.append("?");
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            do {
                Map.Entry<String, String> nextMap = iterator.next();
                url.append(nextMap.getKey()).append("=").append(nextMap.getValue());
                if (iterator.hasNext()) {
                    url.append("&");
                }
            } while (iterator.hasNext());
        }
        this.parseURL(url.toString());
    }
}
