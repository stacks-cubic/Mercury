package cc.stacks.mercury.controller;

import cc.stacks.mercury.service.ZtSocketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/mrp")
public class ProxyController {

    @Value("${mercury.init}")
    private Boolean initState;
    private final ZtSocketService ztSocketService;

    public ProxyController(ZtSocketService ztSocketService) {
        this.ztSocketService = ztSocketService;
    }

    @ResponseBody
    @GetMapping(value = "/**")
    public void proxyGet(HttpServletRequest request, HttpServletResponse response) {
        ztSocketService.httpProxy("192.168.192.1",9820,request, response);
    }

}