package cc.stacks.mercury.data;

import cc.stacks.mercury.model.Group;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@SuppressWarnings("unused")
public interface GroupData {

    @Insert("INSERT INTO `group` (`uid`, `name`, `fold`, `hide`, `date`) VALUES (#{uid},#{name},#{fold},#{hide},#{date})")
    int add(int uid,String name,boolean fold,int hide,long date);

    @Update("UPDATE `group` SET `name` = #{name},`fold` = #{fold},`hide` = #{hide},`date` = #{date} WHERE `id` = #{id}")
    int update(int id,String name,boolean fold,int hide,long date);

    @Delete("DELETE FROM `group` WHERE  `id` = #{id}")
    int delete(int id);

    @Select("SELECT `id`, `uid`, `name`, `weight`, `fold`, `hide`, `date` FROM `group` WHERE `id` = #{id}")
    Group getItem(int id);

    @Select("SELECT `id`, `uid`, `name`, `weight`, `fold`, `hide`, `date` FROM `group` ${screen} ORDER BY `weight` DESC")
    List<Group> getList(String screen);

}