package cc.stacks.mercury.controller;

import cc.stacks.mercury.MercuryApplication;
import cc.stacks.mercury.config.Access;
import cc.stacks.mercury.service.SystemService;
import cc.stacks.mercury.util.TextUtil;
import cc.stacks.mercury.util.Transit;
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
    private boolean initState;
    @Value("${mercury.name}")
    private String sysName;
    @Value("${mercury.version}")
    private String sysVersion;

    private final SystemService systemService;

    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    // 获取初始化数据
    @ResponseBody
    @GetMapping(value = "/init")
    public Transit<Object> initBase() {
        Map<String, String> data = new HashMap<>();
        data.put("name", sysName);
        data.put("version", sysVersion);
        if (!initState) return Transit.failure(10001,data);
        return Transit.success(data);
    }

    // 初始化系统
    @ResponseBody
    @PostMapping(value = "/init")
    public Transit<Object> completeInit(String dbUrl,String dbDriver,String dbUser,String dbPassword,String adminName,String adminNickname,String adminPassword,Boolean registerState,String title) {
        if (initState) return Transit.failure(10002);
        if (TextUtil.isNull(dbUrl)) return Transit.failure(10009,"Database url cannot be empty");
        if (!dbUrl.startsWith("jdbc:")) return Transit.failure(10009,"Invalid database url format");
        if (TextUtil.isNull(dbDriver)) return Transit.failure(10009,"Database driver cannot be empty");
        if (TextUtil.isNull(title)) return Transit.failure(10009,"Title cannot be empty");
        if (!TextUtil.isNull(dbUser) && TextUtil.isNull(dbPassword)) return Transit.failure(10009,"Database password cannot be empty");
        if (TextUtil.isNull(registerState)) registerState = false;
        return systemService.completeInit(dbUrl, dbDriver, dbUser, dbPassword, adminName, adminNickname, adminPassword,registerState, title);
    }

    // 测试连接数据库
    @ResponseBody
    @PostMapping(value = "/init/db")
    public Transit<Object> testDatabase(String dbUrl,String dbUser,String dbPassword) {
        if (initState) return Transit.failure(10002);
        if (TextUtil.isNull(dbUrl)) return Transit.failure(10009,"Database url cannot be empty");
        if (!dbUrl.startsWith("jdbc:")) return Transit.failure(10009,"Invalid database url format");
        if (TextUtil.isNull(dbUser)) return Transit.failure(10009,"Database username cannot be empty");
        if (TextUtil.isNull(dbPassword)) return Transit.failure(10009,"Database password cannot be empty");
        return systemService.testDatabase(dbUrl, dbUser, dbPassword);
    }

    // 重启系统
    @Access(admin = true)
    @ResponseBody
    @GetMapping(value = "/reboot")
    public Transit<Object> reboot() {
        MercuryApplication.restart();
        return Transit.success();
    }

}