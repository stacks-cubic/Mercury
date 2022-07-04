package cc.stacks.mercury.controller;

import cc.stacks.mercury.config.Access;
import cc.stacks.mercury.model.Group;
import cc.stacks.mercury.service.GroupService;
import cc.stacks.mercury.util.TextUtil;
import cc.stacks.mercury.util.Transit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/mark")
public class MarkController {

    @Value("${mercury.init}")
    private boolean initState;

    private final GroupService groupService;

    public MarkController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Access
    @ResponseBody
    @GetMapping(value = "/group/list")
    public Transit<Object> getGroupList(HttpServletRequest request) {
        if (!initState) return Transit.failure(10001);
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        boolean admin = Boolean.parseBoolean(request.getAttribute("admin").toString());
        return Transit.success(groupService.getList(uid, true, admin));
    }

    @Access
    @ResponseBody
    @PostMapping(value = "/group/add")
    public Transit<Object> addGroup(HttpServletRequest request, String name, Boolean fold, Integer hide, Integer weight) {
        if (!initState) return Transit.failure(10001);
        if (TextUtil.isNull(name)) return Transit.failure("Name cannot be empty");
        if (TextUtil.isNull(fold)) return Transit.failure("Fold cannot be empty");
        if (hide == null) return Transit.failure("Hide cannot be empty");
        if (weight == null) return Transit.failure("Weight cannot be empty");
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        return Transit.auto(groupService.add(uid, name, fold, hide, weight));
    }

    @Access
    @ResponseBody
    @GetMapping(value = "/group/{gid}")
    public Transit<Object> getGroupInfo(HttpServletRequest request, @PathVariable Integer gid) {
        if (!initState) return Transit.failure(10001);
        if (TextUtil.isNull(gid)) return Transit.failure("Group id cannot be empty");
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        boolean admin = Boolean.parseBoolean(request.getAttribute("admin").toString());
        Group group = groupService.getItem(gid, uid, admin);
        if (group == null) return Transit.failure();
        return Transit.success(group);
    }

    @Access
    @ResponseBody
    @PostMapping(value = "/group/{gid}")
    public Transit<Object> updateGroup(HttpServletRequest request, @PathVariable Integer gid, String name, Boolean fold, Integer hide, Integer weight) {
        if (!initState) return Transit.failure(10001);
        if (TextUtil.isNull(gid)) return Transit.failure("Group id cannot be empty");
        if (TextUtil.isNull(name)) return Transit.failure("Name cannot be empty");
        if (TextUtil.isNull(fold)) return Transit.failure("Fold cannot be empty");
        if (hide == null) return Transit.failure("Hide cannot be empty");
        if (weight == null) return Transit.failure("Weight cannot be empty");
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        boolean admin = Boolean.parseBoolean(request.getAttribute("admin").toString());
        return Transit.auto(groupService.update(gid, name, fold, hide, weight, uid, admin));
    }

}