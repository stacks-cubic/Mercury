package cc.stacks.mercury.data;

import cc.stacks.mercury.model.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@SuppressWarnings("unused")
public interface ConfigData {

    @Select("SELECT `id`,`key`,`value` FROM `config`")
    List<Config> selectList();

    @Select("SELECT `value` FROM `config` WHERE `key` = #{key}")
    String selectValue(String key);

    @Update("UPDATE `config` SET `value` = #{value} WHERE `key` = #{key}")
    int update(String key,String value);

}
