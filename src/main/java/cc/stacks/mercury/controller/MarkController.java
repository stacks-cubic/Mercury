package cc.stacks.mercury.controller;

import cc.stacks.mercury.config.Access;
import cc.stacks.mercury.model.Group;
import cc.stacks.mercury.model.Mark;
import cc.stacks.mercury.service.GroupService;
import cc.stacks.mercury.service.MarkService;
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
    private final MarkService markService;
    private final GroupService groupService;

    public MarkController(GroupService groupService, MarkService markService) {
        this.groupService = groupService;
        this.markService = markService;
    }

    @Access
    @ResponseBody
    @GetMapping(value = "/group/list")
    public Transit<Object> getGroupList(HttpServletRequest request) {
        return getList(request, true, false);
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
        return getItem(request, gid, true);
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

    @Access
    @ResponseBody
    @DeleteMapping(value = "/group/{gid}")
    public Transit<Object> removeGroup(HttpServletRequest request, @PathVariable Integer gid) {
        return remove(request, gid, true);
    }

    @Access
    @ResponseBody
    @GetMapping(value = "/list")
    public Transit<Object> getMarkList(HttpServletRequest request, Boolean service) {
        if (service == null) service = false;
        return getList(request, false, service);
    }

    @Access
    @ResponseBody
    @PostMapping(value = "/add")
    public Transit<Object> addMark(HttpServletRequest request, Integer gid, String title, String icon, String describe, Integer weight, Boolean service, String era, String ira, Integer hide) {
        Transit<Object> check = checkMark(1, gid, title, describe, weight, era, ira, hide);
        if (!check.isState()) return check;
        if (TextUtil.isNull(icon)) return Transit.failure("Icon cannot be empty");
        if (service == null) service = false;
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        return Transit.auto(markService.add(gid, uid, title, icon, describe, weight, service, era, ira, hide));
    }

    @Access
    @ResponseBody
    @GetMapping(value = "/{mid}")
    public Transit<Object> getMarkInfo(HttpServletRequest request, @PathVariable Integer mid) {
        return getItem(request, mid, false);
    }

    @Access
    @ResponseBody
    @PostMapping(value = "/{mid}")
    public Transit<Object> updateMark(HttpServletRequest request, @PathVariable Integer mid, Integer gid, String title, String describe, Integer weight, String era, String ira, Integer hide) {
        Transit<Object> check = checkMark(mid, gid, title, describe, weight, era, ira, hide);
        if (!check.isState()) return check;
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        boolean admin = Boolean.parseBoolean(request.getAttribute("admin").toString());
        return Transit.auto(markService.update(mid, gid, title, describe, weight, era, ira, hide, uid, admin));
    }

    @Access
    @ResponseBody
    @DeleteMapping(value = "/{mid}")
    public Transit<Object> removeMark(HttpServletRequest request, @PathVariable Integer mid) {
        return remove(request, mid, false);
    }

    private Transit<Object> getList(HttpServletRequest request, boolean isGroup, boolean service) {
        if (!initState) return Transit.failure(10001);
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        boolean admin = Boolean.parseBoolean(request.getAttribute("admin").toString());
        if (isGroup) return Transit.success(groupService.getList(uid, true, admin));
        return Transit.success(markService.getList(uid, service, true, admin));
    }

    private Transit<Object> getItem(HttpServletRequest request, int id, boolean isGroup) {
        if (!initState) return Transit.failure(10001);
        if (TextUtil.isNull(id)) return Transit.failure((isGroup ? "Group" : "Mark") + " id cannot be empty");
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        boolean admin = Boolean.parseBoolean(request.getAttribute("admin").toString());
        if (isGroup) {
            Group group = groupService.getItem(id, uid, admin);
            if (group != null) return Transit.success(group);
        } else {
            Mark mark = markService.getItem(id, uid, admin);
            if (mark != null) return Transit.success(mark);
        }
        return Transit.failure();
    }

    private Transit<Object> remove(HttpServletRequest request, int id, boolean isGroup) {
        if (!initState) return Transit.failure(10001);
        if (TextUtil.isNull(id)) return Transit.failure((isGroup ? "Group" : "Mark") + " id cannot be empty");
        int uid = Integer.parseInt(request.getAttribute("uid").toString());
        boolean admin = Boolean.parseBoolean(request.getAttribute("admin").toString());
        if (isGroup) return Transit.auto(groupService.delete(id, uid, admin));
        return Transit.auto(markService.delete(id, uid, admin));
    }

    private Transit<Object> checkMark(Integer mid, Integer gid, String title, String describe, Integer weight, String era, String ira, Integer hide) {
        if (!initState) return Transit.failure(10001);
        if (TextUtil.isNull(mid)) return Transit.failure("Mark id cannot be empty");
        if (TextUtil.isNull(gid)) return Transit.failure("Group cannot be empty");
        if (TextUtil.isNull(title)) return Transit.failure("Title cannot be empty");
        if (TextUtil.isNull(describe)) return Transit.failure("Describe cannot be empty");
        if (TextUtil.isNull(era)) return Transit.failure("External address cannot be empty");
        if (TextUtil.isNull(ira)) return Transit.failure("Internal address cannot be empty");
        if (weight == null) return Transit.failure("Weight cannot be empty");
        if (hide == null) return Transit.failure("Hide cannot be empty");
        return Transit.success();
    }

}