package cc.stacks.mercury.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {

    // 令牌代码
    private String code;
    // 是否为管理员
    private Boolean admin;
    // 用户编号
    private Integer uid;
    // 是否外网
    private Boolean external;
    // IP 地址
    private String ip;
    // 平台信息
    private String platform;
    // 设备信息
    private String device;
    // 签发时间
    private Long issued;
    // 过期时间
    private Long expire;

}
