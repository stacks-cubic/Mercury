package cc.stacks.mercury.service;

import cc.stacks.mercury.data.MarkData;
import cc.stacks.mercury.model.Mark;
import cc.stacks.mercury.util.TextUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MarkService {

    private final MarkData markData;

    public MarkService(MarkData markData) {
        this.markData = markData;
    }

    /**
     * 获取书签列表
     *
     * @param uid    用户编号
     * @param service 是否为服务书签
     * @param manage 是否为管理场景
     * @param admin  是否为管理员
     * @return 书签列表
     */
    public List<Mark> getList(Integer uid,boolean service, boolean manage, boolean admin) {
        try {
            String sql = "WHERE `service` = "+service+" ";
            // 获取游客书签
            if (TextUtil.isNull(uid)) sql += "AND `hide` IN(0,2)";
                // 管理员获取全部书签
            else if (admin && manage) sql += "";
                // 用户获取自己全部的书签
            else if (manage) sql += "AND `uid` = #{uid}";
                // 获取用户书签
            else sql += "AND (`uid` = #{uid} AND `hide` = 4) OR `hide` IN(0,3)";
            // 查询列表
            return markData.getList(sql);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 获取书签信息
     *
     * @param mid   书签编号
     * @param uid   用户编号
     * @param admin 是否为管理员
     * @return 书签信息
     */
    public Mark getItem(int mid, Integer uid, boolean admin) {
        try {
            Mark mark = markData.getItem(mid);
            // 当前用户为管理员或书签归属于当前用户时返回
            if (admin || (!TextUtil.isNull(uid) && Objects.equals(mark.getUid(), uid))) return mark;
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加书签
     *
     * @param gid      分组编号
     * @param uid      用户编号
     * @param title    书签名称
     * @param icon     图标
     * @param describe 描述
     * @param weight   权重
     * @param service  是否为服务
     * @param era      外部地址
     * @param ira      内部地址
     * @param hide     隐藏 (0不隐藏,1隐藏,2对用户隐藏,3对游客隐藏,4仅添加人可见)
     * @return 添加状态
     */
    public boolean add(int gid, int uid, String title, String icon, String describe, int weight, boolean service, String era, String ira, int hide) {
        try {
            return markData.add(gid, uid, 0, title, icon, describe, weight, service, era, ira, hide, System.currentTimeMillis()) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改书签信息
     *
     * @param mid      书签编号
     * @param gid      分组编号
     * @param title    书签名称
     * @param describe 描述
     * @param weight   权重
     * @param era      外部地址
     * @param ira      内部地址
     * @param hide     隐藏 (0不隐藏,1隐藏,2对用户隐藏,3对游客隐藏,4仅添加人可见)
     * @param uid      用户编号
     * @param admin    是否为管理员
     * @return 修改状态
     */
    public boolean update(int mid, int gid, String title, String describe, int weight, String era, String ira, int hide, int uid, boolean admin) {
        try {
            // 判断是否允许修改
            if (getItem(mid, uid, admin) == null) return false;
            return markData.update(mid, gid, title, describe, weight, era, ira, hide, System.currentTimeMillis()) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除书签
     *
     * @param mid   书签编号
     * @param uid   用户编号
     * @param admin 是否为管理员
     * @return 删除状态
     */
    public boolean delete(int mid, int uid, boolean admin) {
        try {
            // 判断是否允许删除
            if (getItem(mid, uid, admin) == null) return false;
            return markData.delete(mid) == 1;
        } catch (Exception e) {
            return false;
        }
    }
}