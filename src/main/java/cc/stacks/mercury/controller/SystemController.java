package cc.stacks.mercury.controller;

import cc.stacks.mercury.MercuryApplication;
import cc.stacks.mercury.config.Access;
import cc.stacks.mercury.service.SystemService;
import cc.stacks.mercury.util.TextUtil;
import cc.stacks.mercury.util.Transit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/sys")
public class SystemController {

    @Value("${mercury.init}")
    private Boolean initState;
    @Value("${mercury.name}")
    private String sysName;
    @Value("${mercury.version}")
    private String sysVersion;

    private final SystemService systemService;

    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @ResponseBody
    @GetMapping(value = "/init")
    public Transit<Object> initBase() {
        if (!initState) return Transit.failure(10001);
        Map<String, String> data = new HashMap<>();
        data.put("name", sysName);
        data.put("version", sysVersion);
        return Transit.success(data);
    }

    @ResponseBody
    @PostMapping(value = "/init")
    public Transit<Object> completeInit(String dbUrl,String dbDriver,String dbUser,String dbPassword,String adminName,String adminNickname,String adminPassword,String title) {
        if (initState) return Transit.failure(10002);
        if (TextUtil.isNull(dbUrl)) return Transit.failure(10010,"Database Url cannot be empty");
        if (TextUtil.isNull(dbDriver)) return Transit.failure(10010,"Database Driver cannot be empty");
        if (TextUtil.isNull(title)) return Transit.failure(10010,"Title cannot be empty");
        if (!TextUtil.isNull(dbUser) && TextUtil.isNull(dbPassword)) return Transit.failure(10010,"Database Password cannot be empty");
        return systemService.completeInit(dbUrl, dbDriver, dbUser, dbPassword, adminName, adminNickname, adminPassword, title);
    }

    @Access(admin = true)
    @ResponseBody
    @GetMapping(value = "/reboot")
    public Transit<Object> reboot() {
        MercuryApplication.restart();
        return Transit.success();
    }

}