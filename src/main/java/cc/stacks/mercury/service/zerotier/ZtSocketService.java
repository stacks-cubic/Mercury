package cc.stacks.mercury.service.zerotier;

import cc.stacks.mercury.util.TextUtil;
import com.zerotier.sockets.*;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@Service
public class ZtSocketService {

    public void httpProxy(String ip, int port, HttpServletRequest request, HttpServletResponse response) {
        try {
            // 拆解链接
            String uri = request.getRequestURI();
            if (uri.contains("/")) uri = uri.substring(uri.indexOf("/"));
            else uri = "/";
            if (!TextUtil.isNull(request.getQueryString())) uri += "?" + request.getQueryString();
            // 组装请求
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
            // 创建连接
            ZeroTierSocket socket = new ZeroTierSocket(2, ZeroTierNative.ZTS_SOCK_STREAM, 0);
            socket.connect(ip, port);
            // 创建输入流
            ZeroTierOutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            // 发送请求
            dataOutputStream.write(msg.toString().getBytes(StandardCharsets.UTF_8));
            // 创建输出流
            ZeroTierInputStream in = socket.getInputStream();
            // 创建换行标识
            byte[] line = "\r\n".getBytes();
            // 获取输入字节
            ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
            socket.shutdownOutput();
            in.transferTo(dataOut);
            in.close();
            byte[] data = dataOut.toByteArray();
            // 创建字节拆分缓存流
            ByteArrayOutputStream cache = new ByteArrayOutputStream();
            List<ByteArrayOutputStream> outList = new ArrayList<>();
            boolean end = false;
            // 遍历并使用换行符分割输入流
            for (int i = 0; data.length > i; i++) {
                byte now = data[i];
                if (data.length != i + 1 && now == line[0] && data[i + 1] == line[1]) {
                    if (!end && !TextUtil.isNull(cache)) {
                        outList.add(cache);
                        cache = new ByteArrayOutputStream();
                    }
                    if (data.length > i + 3 && data[i + 2] == line[0] && data[i + 3] == line[1]) {
                        end = true;
                        i += 2;
                    }
                    i++;
                } else cache.write(now);
            }
            response.reset();
            // 组装响应头
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
            response.setContentLength(cache.size());
            // 输出响应体
            out(response, cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void out(HttpServletResponse response, ByteArrayOutputStream cache) {
        byte[] all = cache.toByteArray();
        System.out.println(new String(all,StandardCharsets.UTF_8));
        ByteArrayInputStream input = new ByteArrayInputStream(all);
        try {
            int now = 0;
            int max = all.length / 10;
            byte[] data = new byte[10];
            ServletOutputStream outputStream = response.getOutputStream();
            while (max + 1 >= now) {
                now++;
                int number = input.read(data);
                if (number < 0) continue;
                outputStream.write(data, 0, number);
                outputStream.flush();
            }
            input.close();
            outputStream.flush();
        } catch (Exception e) {
            try {
                input.close();
            } catch (Exception ignored) {
            }
        }
    }

}
