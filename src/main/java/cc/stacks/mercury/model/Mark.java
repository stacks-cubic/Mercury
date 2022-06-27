package cc.stacks.mercury.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mark {

    // 编号
    private Integer id;
    // 分组编号
    private Integer gid;
    // 用户编号
    private Integer uid;
    // 服务标识编号
    private Integer ssid;
    // 标题
    private String title;
    // 图标
    private String icon;
    // 颜色(预留)
    private String color;
    // 描述
    private String describe;
    // 权重
    private Integer weight;
    // 上级书签(预留)
    private Integer superior;
    // 是否为服务
    private Boolean service;
    // 外部地址
    private String era;
    // 内部地址
    private String ira;
    // 隐藏 (0不隐藏,1隐藏,2对用户隐藏,3对游客隐藏,4对非添加人隐藏)
    private Integer hide;
    // 添加时间
    private Long date;

}