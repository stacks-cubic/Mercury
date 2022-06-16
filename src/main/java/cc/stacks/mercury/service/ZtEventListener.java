package cc.stacks.mercury.service;

import cc.stacks.mercury.util.LogUtil;
import com.zerotier.sockets.ZeroTierEventListener;
import com.zerotier.sockets.ZeroTierNative;
import org.springframework.scheduling.annotation.Async;

@Async("ZtTask")
public class ZtEventListener implements ZeroTierEventListener {

    @Override
    public void onZeroTierEvent(long id, int code) {
        String tips = "Zt #" + Long.toHexString(id) + ": " ;
        if(code == ZeroTierNative.ZTS_EVENT_NODE_UP) tips += "Node has been initialized"; // 200
        else if(code == ZeroTierNative.ZTS_EVENT_NODE_ONLINE) tips += "Node is online"; // 201
        else if(code == ZeroTierNative.ZTS_EVENT_NODE_OFFLINE) tips += "Node is offline"; // 202
        else if(code == ZeroTierNative.ZTS_EVENT_NETWORK_OK) tips += "Joined the network successfully"; // 213
        else if (code == ZeroTierNative.ZTS_EVENT_NETWORK_ACCESS_DENIED) tips += "The node is not allowed to join the network (you must authorize node)"; // 214
        else if(code == ZeroTierNative.ZTS_EVENT_ADDR_ADDED_IP4) tips += "A new managed IPv4 address was assigned to this peer"; // 260
        else if (code == ZeroTierNative.ZTS_EVENT_STORE_PLANET) tips += "The node has received an updated planet config"; // 272
        else if (code == ZeroTierNative.ZTS_EVENT_NETWORK_NOT_FOUND) {
            ZtCoreService.stopNode();
        }
        else return;
        // 输出其他代码
        // else tips +=" code is "+code;
        LogUtil.info(tips);
    }

}