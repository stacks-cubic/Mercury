package cc.stacks.mercury.service;

import cc.stacks.mercury.data.UserData;
import cc.stacks.mercury.model.User;
import cc.stacks.mercury.util.SecurityUtil;
import cc.stacks.mercury.util.TOTPUtil;
import cc.stacks.mercury.util.TextUtil;
import cc.stacks.mercury.util.Transit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserData userData;

    public UserService(UserData userData) {
        this.userData = userData;
    }

    /**
     * 登录账户
     * @param name 用户名
     * @param password 密码
     * @param code 验证码
     * @return 登录状态
     */
    public Transit<Object> login(String name, String password, String code) {
        try {
            User user = userData.getPrivacyItem(name, password);
            if (!TextUtil.isNull(user.getMfa())) {
                if (TextUtil.isNull(code)) return Transit.failure("请输入双因素验证码");
                if (!TOTPUtil.valid(user.getMfa(),code)) return Transit.failure("双因素验证码错误");
            }
            // TODO 签发令牌
            return Transit.success();
        } catch (Exception e) {
            return Transit.failure();
        }
    }

    /**
     * 添加用户
     *
     * @param name     用户名
     * @param nickname 昵称
     * @param password 密码
     * @param admin    是否为管理员
     * @param mfa      是否启用双因素
     * @return 添加状态
     */
    public String add(String name, String nickname, String password, boolean admin, boolean mfa) {
        try {
            String totp = null;
            if (mfa) totp = TOTPUtil.getKey();
            if (userData.add(name, nickname, password, admin, totp, System.currentTimeMillis()) == 1) return totp;
            return totp;
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * 修改用户信息
     *
     * @param uid      用户编号
     * @param name     用户名
     * @param nickname 昵称
     * @param password 密码
     * @param admin    是否为管理员
     * @return 修改状态
     */
    public boolean update(int uid, String name, String nickname, String password, boolean admin) {
        try {
            if (TextUtil.isNull(password)) password = "";
            else password = "`password` = \"" + SecurityUtil.digestMD5(password) + "\",";
            return userData.update(uid, name, nickname, password, admin) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除用户
     *
     * @param uid 用户编号
     * @return 删除状态
     */
    public boolean delete(int uid) {
        try {
            if (userData.delete(uid) == 1) {
                // TODO 删除用户产生的数据
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取用户信息
     *
     * @param uid 用户编号
     * @return 用户信息
     */
    public User getItem(int uid) {
        try {
            User user = userData.getItem(uid);
            user.setMfa("" + !TextUtil.isNull(user.getMfa()));
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    public List<User> getList() {
        try {
            List<User> list = userData.getList();
            for (User user : list) {
                user.setMfa("" + !TextUtil.isNull(user.getMfa()));
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

}