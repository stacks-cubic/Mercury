package cc.stacks.mercury.controller;

import cc.stacks.mercury.config.Access;
import cc.stacks.mercury.model.User;
import cc.stacks.mercury.service.UserService;
import cc.stacks.mercury.util.SecurityUtil;
import cc.stacks.mercury.util.Transit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {

    @Value("${mercury.init}")
    private boolean initState;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping(value = "/auth/login")
    public Transit<Object> login(HttpServletRequest request, String name, String password, String code) {
        if (!initState) return Transit.failure(10001);
        return userService.login(name, password, code, request.getHeader("User-Agent"), SecurityUtil.extractIP(request));
    }

    @Access
    @ResponseBody
    @GetMapping(value = "/my/info")
    public Transit<Object> getMyInfo(HttpServletRequest request) {
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        User user = userService.getItem(uid);
        if (user == null) return Transit.failure();
        return Transit.success(user);
    }

}