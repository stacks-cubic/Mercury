package cc.stacks.mercury.controller;

import cc.stacks.mercury.MercuryApplication;
import cc.stacks.mercury.config.Access;
import cc.stacks.mercury.config.SystemConfig;
import cc.stacks.mercury.model.setting.P1;
import cc.stacks.mercury.service.SettingService;
import cc.stacks.mercury.service.SystemService;
import cc.stacks.mercury.util.SecurityUtil;
import cc.stacks.mercury.util.TextUtil;
import cc.stacks.mercury.util.Transit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/setting")
public class SettingController {

    @Value("${mercury.init}")
    private boolean initState;

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    // 更新个性化设置
    @Access(admin = true)
    @ResponseBody
    @PostMapping(value = "/update/p1")
    public Transit<Object> updateP1(P1 input) {
        if (!initState) return Transit.failure(10001);
        if (TextUtil.isNull(input.getDark())) return Transit.failure(10009, "Dark mode cannot be empty");
        if (TextUtil.isNull(input.getColor())) return Transit.failure(10009, "Theme color cannot be empty");
        if (TextUtil.isNull(input.getTextSize())) return Transit.failure(10009, "Text size cannot be empty");
        if (TextUtil.isNull(input.getSwitchImage())) return Transit.failure(10009, "Image switch cannot be empty");
        if (TextUtil.isNull(input.getAutoImage())) return Transit.failure(10009, "Image auto cannot be empty");
        else if (input.getAutoImage() && TextUtil.isNull(input.getImageSource()))
            return Transit.failure(10009, "Image source cannot be empty");
        else if (!input.getAutoImage() && TextUtil.isNull(input.getImage()))
            return Transit.failure(10009, "Image cannot be empty");
        if (TextUtil.isNull(input.getToolsStyle())) return Transit.failure(10009, "Tools style cannot be empty");
        if (TextUtil.isNull(input.getServiceStyle())) return Transit.failure(10009, "Service style cannot be empty");
        if (TextUtil.isNull(input.getMarkStyle())) return Transit.failure(10009, "Mark style cannot be empty");
        if (TextUtil.isNull(input.getPhrase())) return Transit.failure(10009, "Phrase state cannot be empty");
        else if (input.getPhrase() && TextUtil.isNull(input.getPhraseApi())) return Transit.failure(10009, "Phrase api cannot be empty");

        return Transit.auto(settingService.updateP1(input));
    }

}