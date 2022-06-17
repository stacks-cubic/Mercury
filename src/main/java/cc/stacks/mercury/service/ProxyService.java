package cc.stacks.mercury.service;

import cc.stacks.mercury.util.TextUtil;
import com.zerotier.sockets.ZeroTierInputStream;
import com.zerotier.sockets.ZeroTierNative;
import com.zerotier.sockets.ZeroTierOutputStream;
import com.zerotier.sockets.ZeroTierSocket;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ProxyService {

    public void run(String ip, int port, HttpServletRequest request, HttpServletResponse response) {
        ZeroTierOutputStream proxyOutput = null;
        ZeroTierInputStream proxyInput = null;
        OutputStream clientOutput = null;
        InputStream clientInput = null;
        ZeroTierSocket socket = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<ByteArrayOutputStream> outList = new ArrayList<>();
        try {
            // 创建连接
            socket = new ZeroTierSocket(2, ZeroTierNative.ZTS_SOCK_STREAM, 0);
            socket.setSoTimeout(10000);
            socket.connect(ip, port);
            // 创建输入流
            proxyOutput = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(proxyOutput);
            // 发送请求
            clientInput = request.getInputStream();
            if (request.getMethod().equalsIgnoreCase("connect")) {
                dataOutputStream.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
                dataOutputStream.flush();
            } else dataOutputStream.write(buildRequest(ip, port, request).getBytes(StandardCharsets.UTF_8));

            socket.shutdownOutput();
            proxyInput = socket.getInputStream();
            boolean end = false;
            boolean next = false;
            while (true) {
                int num = proxyInput.read();
                if (!end && num == 13) next = true;
                else if (!end && next && num == 10) {
                    if (out.size() > 1) {
                        outList.add(out);
                        out = new ByteArrayOutputStream();
                    } else end = true;
                    next = false;
                } else {
                    out.write(num);
                    next = false;
                }
            }
        } catch (IOException e) {
            try {
                clientOutput = response.getOutputStream();
                response.reset();
                buildHeader(response, outList,out.size());
                clientOutput.write(out.toByteArray());
                clientOutput.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            if (proxyInput != null) {
                try {
                    proxyOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (proxyOutput != null) {
                try {
                    proxyOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientInput != null) {
                try {
                    clientInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String buildRequest(String ip, int port, HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (!TextUtil.isNull(request.getQueryString())) uri += "?" + request.getQueryString();
        StringBuilder msg = new StringBuilder(request.getMethod().toUpperCase());
        msg.append(" ").append(uri).append(" ").append(request.getProtocol()).append("\r\n");
        msg.append("Host: ").append(ip).append(":").append(port).append("\r\n");
        Enumeration<String> names = request.getHeaderNames();
        for (Iterator<String> it = names.asIterator(); it.hasNext(); ) {
            String name = it.next();
            if (name.equalsIgnoreCase("host")) continue;
            msg.append(name).append(": ").append(request.getHeader(name)).append("\r\n");
        }
        msg.append("\r\n");
        return msg.toString();
    }

    public void buildHeader(HttpServletResponse response, List<ByteArrayOutputStream> outList,int size) {
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
                else if (!TextUtil.isNull(key) && !TextUtil.isNull(value)) response.setHeader(key, value);
            }
        }
        if (TextUtil.isNull(response.getHeader("Content-Length"))){
            response.setContentLength(size);
//            response.setHeader("Content-Encoding","gzip");
        }
    }

}