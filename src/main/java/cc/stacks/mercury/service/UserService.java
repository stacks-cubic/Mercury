package cc.stacks.mercury.service;

import cc.stacks.mercury.data.TokenData;
import cc.stacks.mercury.data.UserData;
import cc.stacks.mercury.model.User;
import cc.stacks.mercury.util.SecurityUtil;
import cc.stacks.mercury.util.TOTPUtil;
import cc.stacks.mercury.util.TextUtil;
import cc.stacks.mercury.util.Transit;
import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserData userData;
    private final TokenData tokenData;
    private final UserAgentAnalyzer uaa;
    private final Cache<String, Object> caffe;

    public UserService(UserData userData, TokenData tokenData, UserAgentAnalyzer uaa, Cache<String, Object> caffe) {
        this.userData = userData;
        this.tokenData = tokenData;
        this.uaa = uaa;
        this.caffe = caffe;
    }

    /**
     * 登录账户
     *
     * @param name     用户名
     * @param password 密码
     * @param code     验证码
     * @return 登录状态
     */
    public Transit<Object> login(String name, String password, String code, String agent, String ip) {
        try {
            // 查询用户隐私信息
            User user = userData.getPrivacyItem(name, password);
            // 判断是否启用了双因素
            if (!TextUtil.isNull(user.getMfa())) {
                if (TextUtil.isNull(code)) return Transit.failure("请输入双因素验证码");
                if (!TOTPUtil.valid(user.getMfa(), code)) return Transit.failure("双因素验证码错误");
            }
            // 获取当前时间
            long now = System.currentTimeMillis();
            // 解析用户代理
            UserAgent userAgent = uaa.parse(agent);
            // 获取访问平台
            String platform = userAgent.get(userAgent.AGENT_NAME_VERSION).getValue();
            // 获取访问设备
            String device = userAgent.get(userAgent.DEVICE_CLASS).getValue();
            device += "|" + userAgent.get(userAgent.OPERATING_SYSTEM_NAME).getValue();
            // 签发令牌
            String token = SecurityUtil.digestMD5("M:" + user.getId() + ":" + platform + ":" + device + ":" + ip + (TextUtil.isNull(code) ? "" : ":" + code) + "$" + now);
            // 存储令牌
            if (tokenData.add(token, user.getId(), !SecurityUtil.isLocalHost(ip), ip, platform, device, now, now + (60000 * 60 * 24 * 7)) == 1) {
                caffe.put("token:" + token, JSON.toJSONString(tokenData.getItem(token)));
                caffe.put("agent:" + SecurityUtil.digestMD5(agent), "{\"platform\":\"" + platform + "\",\"device\":\"" + device + "\"}");
                return Transit.success(token);
            }
            return Transit.failure();
        } catch (Exception e) {
            e.printStackTrace();
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