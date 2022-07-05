package cc.stacks.mercury.controller;

import cc.stacks.mercury.config.Access;
import cc.stacks.mercury.model.User;
import cc.stacks.mercury.service.UserService;
import cc.stacks.mercury.util.SecurityUtil;
import cc.stacks.mercury.util.TOTPUtil;
import cc.stacks.mercury.util.TextUtil;
import cc.stacks.mercury.util.Transit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        if (!initState) return Transit.failure(10001);
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        User user = userService.getItem(uid);
        if (user == null) return Transit.failure();
        return Transit.success(user);
    }

    @Access(admin = true)
    @ResponseBody
    @GetMapping(value = "/list")
    public Transit<Object> getList() {
        if (!initState) return Transit.failure(10001);
        return Transit.success(userService.getList());
    }

    @Access(admin = true)
    @ResponseBody
    @GetMapping(value = "/{uid}")
    public Transit<Object> getItem(@PathVariable Integer uid) {
        if (!initState) return Transit.failure(10001);
        User user = userService.getItem(uid);
        if (user==null) return Transit.failure();
        return Transit.success(user);
    }

    @Access(admin = true)
    @ResponseBody
    @GetMapping(value = "/mfa")
    public Transit<Object> getMFAKey() {
        return Transit.success(TOTPUtil.getKey());
    }

    @Access(admin = true)
    @ResponseBody
    @PostMapping(value = "/{uid}")
    public Transit<Object> update(@PathVariable Integer uid, String name, String nickname, String password, Boolean admin, Boolean mfa, String mfaKey, String mfaCode) {
        if (!initState) return Transit.failure(10001);
        if (TextUtil.isNull(uid)) return Transit.failure("User id cannot be empty");
        if (TextUtil.isNull(name)) return Transit.failure("User name cannot be empty");
        if (TextUtil.isNull(nickname)) return Transit.failure("User nickname cannot be empty");
        if (!TextUtil.isNull(mfa) && mfa && !TextUtil.isNull(mfaKey)) {
            if (TextUtil.isNull(mfaCode)) return Transit.failure("MFA code cannot be empty");
            if(!TOTPUtil.valid(mfaKey,mfaCode)) return Transit.failure("Invalid MFA code");
        }
        if (admin==null) admin = false;
        return Transit.auto(userService.update(uid, name,nickname,password,admin,mfa,mfaKey));
    }
}