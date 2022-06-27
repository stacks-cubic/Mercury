package cc.stacks.mercury.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group {

    // 编号
    private Integer id;
    // 用户编号
    private Integer uid;
    // 分组名称
    private String name;
    // 权重
    private Integer weight;
    // 是否折叠
    private Boolean fold;
    // 隐藏 (0不隐藏,1隐藏,2对用户隐藏,3对游客隐藏,4仅添加人可见)
    private Integer hide;
    // 添加时间
    private Long date;

}
