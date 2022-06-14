package cc.stacks.mercury.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {

    // 配置编号
    private Integer id;
    // 配置项
    private String key;
    // 配置内容
    private String value;

}
