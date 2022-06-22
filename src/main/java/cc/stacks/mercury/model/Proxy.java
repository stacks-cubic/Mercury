package cc.stacks.mercury.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Proxy {

    // 编号
    private Integer id;
    // 名称
    private String name;
    // 主机地址
    private String host;
    // 端口号
    private Integer port;
    // 访问来源
    private String source;
    // 代理模式(1直连代理,2穿透代理)
    private Integer mode;

}
