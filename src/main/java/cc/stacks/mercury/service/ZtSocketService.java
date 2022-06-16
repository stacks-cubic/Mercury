package cc.stacks.mercury.service;

import cc.stacks.mercury.util.TextUtil;
import com.zerotier.sockets.*;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
            String uri = request.getRequestURI().substring(5);
            if (uri.contains("/")) uri = uri.substring(uri.indexOf("/"));
            else uri = "/";
            if (!TextUtil.isNull(request.getQueryString())) uri += "?" + request.getQueryString();
            // 组装请求
            StringBuilder msg = new StringBuilder("GET ");
            msg.append(uri).append(" ").append(request.getProtocol()).append("\r\n");
            System.out.println(msg);
            msg.append("Host: ").append(ip).append(":").append(port).append("\r\n");
            msg.append("Accept: */*\r\n");
            Enumeration<String> names = request.getHeaderNames();
            for (Iterator<String> it = names.asIterator(); it.hasNext(); ) {
                String name = it.next();
                if (name.equalsIgnoreCase("host") || name.equalsIgnoreCase("Accept")) continue;
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
            socket.shutdownOutput();
            // 创建换行标识
            byte[] line = "\r\n".getBytes();
            // 获取输入字节
            ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
            in.transferTo(dataOut);
            byte[] data = dataOut.toByteArray();
//            byte[] data = getOutputStream(in);
            // 创建字节拆分缓存流
            ByteArrayOutputStream cache = new ByteArrayOutputStream();
            List<ByteArrayOutputStream> outList = new ArrayList<>();
            // 遍历并使用换行符分割输入流
            for (int i = 0; data.length > i; i++) {
                if (data[i] == line[0] && data.length != i + 1 && data[i + 1] == line[1]) {
                    if (!TextUtil.isNull(cache)) {
                        outList.add(cache);
                        cache = new ByteArrayOutputStream();
                    }
                    i++;
                } else cache.write(data[i]);
            }
            // 组装响应头
            for (int i = 0; i < outList.size()-1; i++) {
                ByteArrayOutputStream headOut = outList.get(i);
                String resMsg = headOut.toString();
                if (i == 0) {
                    String[] head = resMsg.split(" ");
                    response.setStatus(Integer.parseInt(head[1]));
                } else if (resMsg.contains(":")){
                    System.out.println(resMsg.substring(0, resMsg.indexOf(":")));
                    response.setHeader(resMsg.substring(0, resMsg.indexOf(":")), resMsg.substring(resMsg.indexOf(":") + 1).trim());
                }
            }
            // 获取响应输出流
            ServletOutputStream out = response.getOutputStream();
            // 输出响应体
            out.write(cache.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getOutputStream(ZeroTierInputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[2048];
            int n;
            long start = System.currentTimeMillis();
            while ((n = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return outputStream.toByteArray();
    }

}
