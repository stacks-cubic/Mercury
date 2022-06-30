package cc.stacks.mercury.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/mark")
public class MarkController {

    @Value("${mercury.init}")
    private boolean initState;

}