package cc.stacks.mercury.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    // 编号
    private Integer id;
    // 头像
    private String avatar;
    // 用户名
    private String name;
    // 昵称
    private String nickname;
    // 密码
    private String password;
    // 是否为管理员
    private Boolean admin;
    // 双因素验证
    private String mfa;
    // 添加时间
    private Long addTime;
    // 上次登录时间
    private Long lastLogin;

}