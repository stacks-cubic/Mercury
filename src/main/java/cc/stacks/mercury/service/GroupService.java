package cc.stacks.mercury.service;

import cc.stacks.mercury.data.GroupData;
import cc.stacks.mercury.model.Group;
import cc.stacks.mercury.util.TextUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GroupService {

    private final GroupData groupData;

    public GroupService(GroupData groupData) {
        this.groupData = groupData;
    }

    /**
     * 获取分组列表
     *
     * @param uid    用户编号
     * @param manage 是否为管理场景
     * @param admin  是否为管理员
     * @return 分组列表
     */
    public List<Group> getList(Integer uid, boolean manage, boolean admin) {
        try {
            String sql = "";
            // 获取游客分组
            if (TextUtil.isNull(uid)) sql = "WHERE `hide` IN(0,2)";
                // 管理员获取全部分组
            else if (admin && manage) sql = "";
                // 用户获取自己全部的分组
            else if (manage) sql = "WHERE `uid` = #{uid}";
                // 获取用户分组
            else sql = "WHERE (`uid` = #{uid} AND `hide` = 4) OR `hide` IN(0,3)";
            // 查询列表
            return groupData.getList(sql);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 获取分组信息
     *
     * @param gid   分组编号
     * @param uid   用户编号
     * @param admin 是否为管理员
     * @return 分组信息
     */
    public Group getItem(int gid, Integer uid, boolean admin) {
        try {
            Group group = groupData.getItem(gid);
            // 当前用户为管理员或分组归属于当前用户时返回
            if (admin || (!TextUtil.isNull(uid) && Objects.equals(group.getUid(), uid))) return group;
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加分组
     *
     * @param uid  用户编号
     * @param name 分组名称
     * @param fold 默认折叠
     * @param hide 隐藏条件
     * @return 添加状态
     */
    public boolean add(int uid, String name, boolean fold, int hide) {
        try {
            return groupData.add(uid, name, fold, hide, System.currentTimeMillis()) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改分组信息
     *
     * @param gid   分组编号
     * @param name  分组名称
     * @param fold  默认折叠
     * @param hide  隐藏条件
     * @param uid   用户编号
     * @param admin 是否为管理员
     * @return 修改状态
     */
    public boolean update(int gid, String name, boolean fold, int hide, int uid, boolean admin) {
        try {
            // 判断是否允许修改
            if (getItem(gid, uid, admin) == null) return false;
            return groupData.update(gid, name, fold, hide, System.currentTimeMillis()) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除分组
     *
     * @param gid   分组编号
     * @param uid   用户编号
     * @param admin 是否为管理员
     * @return 删除状态
     */
    public boolean delete(int gid, int uid, boolean admin) {
        try {
            // 判断是否允许删除
            if (getItem(gid, uid, admin) == null) return false;
            return groupData.delete(gid) == 1;
        } catch (Exception e) {
            return false;
        }
    }

}