package cc.stacks.mercury.service.zerotier;

import cc.stacks.mercury.config.SystemConfig;
import cc.stacks.mercury.util.LogUtil;
import cc.stacks.mercury.util.TextUtil;
import com.zerotier.sockets.*;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class ZtCoreService {

    private static ZeroTierNode node;
    public static String ipv4;

    /**
     * 初始化
     */
    @Async("ZtTask")
    public void init() {
        // 获取代理模式
        int proxyMode = SystemConfig.getInt("network:proxy:mode");
        if (proxyMode != 2) return;
        // 获取配置信息
        String config = SystemConfig.get("network:zt:id");
        if (TextUtil.isNull(config)) {
            LogUtil.info("No ZeroTier config detected");
            return;
        }
        LogUtil.info("Zt #0: Node starting...");
        // 启动节点并等待上线
        boolean state = startNode();
        if (!state) return;
        while (!node.isOnline()) {
            ZeroTierNative.zts_util_delay(1000);
        }
        // 获取网络编号
        long networkId = new BigInteger(config, 16).longValue();
        LogUtil.info("Zt #" + Long.toHexString(node.getId()) + ": Connected to " + config + "...");
        // 加入网络并等待连接就绪
        node.join(networkId);
        while (!node.isNetworkTransportReady(networkId)) {
            ZeroTierNative.zts_util_delay(1000);
        }
        ipv4 = node.getIPv4Address(networkId).getHostAddress();
        LogUtil.info("Zt #" + Long.toHexString(node.getId()) + ": Network connected, IP " + ipv4);
    }

    /**
     * 启动节点
     *
     * @return 启动状态
     */
    public static boolean startNode() {
        try {
            if (node != null) stopNode();
            node = new ZeroTierNode();
            String path = new ApplicationHome().getDir().getPath();
            node.initFromStorage(path + "/zt");
            node.initSetEventHandler(new ZtEventListener());
            node.start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 停止节点
     *
     * @return 停止状态
     */
    public static boolean stopNode() {
        try {
            LogUtil.info("Zt #" + Long.toHexString(node.getId()) + ": Node close");
            node.stop();
            node = null;
            ipv4 = null;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取状态
     *
     * @return 状态码 (-1发生异常,0未配置,1节点未启动,2网络未连接,3网络已连接)
     */
    public static int getState() {
        try {
            String config = SystemConfig.get("network:zt:id");
            if (TextUtil.isNull(config)) return 0;
            long networkId = new BigInteger(config, 16).longValue();
            if (!node.isOnline()) return 1;
            if (!node.isNetworkTransportReady(networkId)) return 2;
            return 3;
        } catch (Exception e) {
            return -1;
        }
    }

}