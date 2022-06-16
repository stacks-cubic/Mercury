package cc.stacks.mercury.controller;

import cc.stacks.mercury.service.ZtCoreService;
import cc.stacks.mercury.util.Transit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/network")
public class NetworkController {

    @Value("${mercury.init}")
    private Boolean initState;
    private final ZtCoreService ztCoreService;

    public NetworkController(ZtCoreService ztCoreService) {
        this.ztCoreService = ztCoreService;
    }

    // 启动Zt
    @ResponseBody
    @GetMapping(value = "/zt/start")
    public Transit<Object> startZt() {
        if (!initState) return Transit.failure(10001);
        ztCoreService.init();
        return Transit.success();
    }

    // 停止节点
    @ResponseBody
    @GetMapping(value = "/zt/stop")
    public Transit<Object> stopZt() {
        if (!initState) return Transit.failure(10001);
        return Transit.auto(ZtCoreService.stopNode());
    }

    // 获取网络状态
    @ResponseBody
    @GetMapping(value = "/zt/state")
    public Transit<Object> getState() {
        if (!initState) return Transit.failure(10001);
        return Transit.success(ZtCoreService.ipv4);
    }

}