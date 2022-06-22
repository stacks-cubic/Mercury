package cc.stacks.mercury.service;

import cc.stacks.mercury.util.LogUtil;
import cc.stacks.mercury.util.TextUtil;
import com.zerotier.sockets.ZeroTierNative;
import com.zerotier.sockets.ZeroTierOutputStream;
import com.zerotier.sockets.ZeroTierSocket;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ProxyService {

    /**
     * 穿透代理
     *
     * @param ip       目标地址
     * @param port     目标端口
     * @param request  请求
     * @param response 响应
     */
    public void pierce(String ip, int port, HttpServletRequest request, HttpServletResponse response) {
        ZeroTierSocket socket = null;
        try {
            // 创建连接
            socket = new ZeroTierSocket(2, ZeroTierNative.ZTS_SOCK_STREAM, 0);
            socket.setSoTimeout(10000);
            socket.connect(ip, port);
            // 创建输入流
            ZeroTierOutputStream proxyOutput = socket.getOutputStream();
            // 发送请求
            send(ip, port, request, new DataOutputStream(proxyOutput));
            // 关闭输入流
            proxyOutput.close();
            // 处理Http响应
            handleHttp(response, socket.getInputStream());
        } catch (Exception e) {
            LogUtil.warn("Zerotier Socket 连接异常");
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // 连接异常
                }
            }
        }
    }

    /**
     * 直连代理
     *
     * @param ip       目标地址
     * @param port     目标端口
     * @param request  请求
     * @param response 响应
     */
    public void direct(String ip, int port, HttpServletRequest request, HttpServletResponse response) {
        try (Socket socket = new Socket(ip, port)) {
            // 创建连接
            socket.setSoTimeout(10000);
            // 创建输入流
            OutputStream proxyOutput = socket.getOutputStream();
            // 发送请求
            send(ip, port, request, new DataOutputStream(proxyOutput));
            // 处理Http响应
            handleHttp(response, socket.getInputStream());
        } catch (Exception e) {
            LogUtil.warn("Socket 连接异常");
        }
    }

    /**
     * 处理http响应
     *
     * @param response 响应
     * @param in       输入流
     */
    private void handleHttp(HttpServletResponse response, InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<ByteArrayOutputStream> header = new ArrayList<>();
        try {
            long run = 0;
            boolean end = false;
            boolean next = false;
            while (true) {
                int num = in.read();
                if (!end && num == 13) next = true;
                else if (!end && next && num == 10) {
                    if (out.size() > 1) {
                        header.add(out);
                        out = new ByteArrayOutputStream();
                    } else end = true;
                    next = false;
                } else if (num != -1) {
                    out.write(num);
                    next = false;
                }else if(run == 0) break;
                run++;
            }
        } catch (IOException e) {
            receive(response, header, out);
        } finally {
            try {
                if (in != null) in.close();
            } catch (Exception ignored) {
                // 流异常
            }
        }
    }

    /**
     * 发送请求
     *
     * @param ip      目标地址
     * @param port    目标端口
     * @param request 请求
     * @param data    数据输出流
     */
    private void send(String ip, int port, HttpServletRequest request, DataOutputStream data) {
        try {
            if (request.getMethod().equalsIgnoreCase("connect")) {
                data.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
                data.flush();
            } else data.write(buildRequest(ip, port, request).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            // 数据流异常
        }
    }

    /**
     * 接收响应
     *
     * @param response 响应
     * @param header   响应头
     * @param out      响应体
     */
    private void receive(HttpServletResponse response, List<ByteArrayOutputStream> header, ByteArrayOutputStream out) {
        try {
            OutputStream clientOutput = response.getOutputStream();
            response.reset();
            // 构建响应头
            buildHeader(response, header, out.size());
            // 解码响应体
            out = encoding(response, out);
            // 输出响应
            if (out.size() > 1) clientOutput.write(out.toByteArray());
            else out.close();
            // 刷新流
            clientOutput.flush();
        } catch (IOException ex) {
            // 连接断开
        } finally {
            if (out != null) try {
                out.close();
            } catch (Exception ignored) {
                // 流异常
            }
        }
    }

    /**
     * 解码
     * @param response 响应
     * @param out 输出
     * @return 解码后的字节流
     */
    private ByteArrayOutputStream encoding(HttpServletResponse response, ByteArrayOutputStream out) {
        try {
            String tag = response.getHeader("Transfer-Encoding");
            if (out.size() == 0 || TextUtil.isNull(tag)) return out;
            if (tag.equals("chunked")) {
                int length = 0;
                byte[] code = out.toByteArray();
                StringBuilder body = new StringBuilder();
                ByteArrayOutputStream cache = new ByteArrayOutputStream();
                for (int i = 0; i < code.length; i++) {
                    if (i < length) cache.write(code[i]);
                    else if (i == length && cache.size() > 0) {
                        cache.write(code[i]);
                        cache.write(code[i + 1]);
                        body.append(cache.toString(StandardCharsets.UTF_8));
                        cache.close();
                        cache = new ByteArrayOutputStream();
                        i++;
                    } else if (cache.size() > 0 && code.length >= i + 1 && code[i] == 13 && code[i + 1] == 10) {
                        length = Integer.decode("0x" + cache.toString(StandardCharsets.UTF_8).replace("\r\n","")) + i;
                        cache.close();
                        cache = new ByteArrayOutputStream();
                        i++;
                    } else cache.write(code[i]);
                }
                out.close();
                cache.close();
                cache = new ByteArrayOutputStream();
                cache.writeBytes(body.toString().getBytes(StandardCharsets.UTF_8));
                return cache;
            }
            return out;
        } catch (Exception e) {
            return out;
        }
    }

    /**
     * 构建请求
     *
     * @param ip      目标地址
     * @param port    目标端口
     * @param request 请求
     * @return 请求内容
     */
    private String buildRequest(String ip, int port, HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (!TextUtil.isNull(request.getQueryString())) uri += "?" + request.getQueryString();
        StringBuilder msg = new StringBuilder(request.getMethod().toUpperCase());
        msg.append(" ").append(uri).append(" ").append(request.getProtocol()).append("\r\n");
        msg.append("Host: ").append(ip).append(":").append(port).append("\r\n");
        Enumeration<String> names = request.getHeaderNames();
        for (Iterator<String> it = names.asIterator(); it.hasNext(); ) {
            String name = it.next();
            if (name.equalsIgnoreCase("host") || name.equalsIgnoreCase("accept-encoding")) continue;
            msg.append(name).append(": ").append(request.getHeader(name)).append("\r\n");
        }
        msg.append("\r\n");
        if (request.getMethod().equalsIgnoreCase("post")) {
            try {
                BufferedReader br = request.getReader();
                String str;
                while ((str = br.readLine()) != null) {
                    msg.append(str);
                }
            } catch (Exception e) {
                // 读取器异常
            }
        }
        return msg.toString();
    }

    /**
     * 构建响应头
     *
     * @param response 响应
     * @param outList  输出列表
     * @param size     输出大小
     */
    private void buildHeader(HttpServletResponse response, List<ByteArrayOutputStream> outList, int size) {
        for (int i = 0; i < outList.size(); i++) {
            ByteArrayOutputStream headOut = outList.get(i);
            String resMsg = headOut.toString();
            if (i == 0) {
                String[] head = resMsg.split(" ");
                response.setStatus(Integer.parseInt(head[1]));
            } else if (resMsg.contains(": ")) {
                String key = resMsg.substring(0, resMsg.indexOf(": "));
                String value = resMsg.substring(resMsg.indexOf(": ") + 1).trim();
                if (key.equalsIgnoreCase("content-type")) response.setContentType(value);
                else if (key.equalsIgnoreCase("content-length")) response.setContentLength(Integer.parseInt(value));
                else if (key.equalsIgnoreCase("set-cookie")) {
                    String name = value.substring(0, value.indexOf("="));
                    String text = value.substring(value.indexOf("=") + 1, value.indexOf(";")).trim();
                    Cookie cookie = new Cookie(name, text);
                    cookie.setHttpOnly(value.contains("HttpOnly"));
                    cookie.setSecure(value.contains("Secure"));

                    if (value.contains("Path=")) {
                        String path = value.substring(value.indexOf("Path=") + 5);
                        cookie.setPath(path.substring(0, path.indexOf(";")));
                    }

                    if (value.contains("Max-Age=")) {
                        String age = value.substring(value.indexOf("Max-Age=") + 8);
                        cookie.setMaxAge(Integer.parseInt(age.substring(0, age.indexOf(";"))));
                    }

                    response.addCookie(cookie);
                } else if (!TextUtil.isNull(key) && !TextUtil.isNull(value)) response.setHeader(key, value);
            }
        }
        if (TextUtil.isNull(response.getHeader("Content-Length"))) response.setContentLength(size);
    }

}