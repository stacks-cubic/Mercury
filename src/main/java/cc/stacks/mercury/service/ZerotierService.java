package cc.stacks.mercury.service;

import com.zerotier.sockets.*;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;

@Service
public class ZerotierService {

    // 这是一个zt的测试方法 - 内网穿透 and 反向代理...
    public void test() {
        try {
            ZeroTierNode node = new ZeroTierNode();
            // 加载身份标识
            // node.initFromStorage(storagePath);

            // 启动
            node.start();
            // 等待上线
            while (!node.isOnline()) {
                ZeroTierNative.zts_util_delay(1000);
            }
            System.out.println("Node ID: " + Long.toHexString(node.getId()));
            long networkId = 0xb6079f73c69c2e9cL;
            // 加入网络
            node.join(networkId);
            while (!node.isNetworkTransportReady(networkId)) {
                ZeroTierNative.zts_util_delay(1000);
            }
            System.out.println("IPv4 address: " + node.getIPv4Address(networkId).getHostAddress());
            System.out.println("IPv6 address: " + node.getIPv6Address(networkId).getHostAddress());

            // TCP Server
            // ZeroTierServerSocket listener = new ZeroTierServerSocket(10980);
            // ZeroTierSocket conn = listener.accept();
            // ZeroTierInputStream inputStream = conn.getInputStream();
            // DataInputStream dataInputStream = new DataInputStream(inputStream);
            // String message = dataInputStream.readUTF();
            // System.out.println("RX: " + message);
            // listener.close();
            // conn.close();

            node.stop();
        } catch (Exception e) {

        }
    }

}