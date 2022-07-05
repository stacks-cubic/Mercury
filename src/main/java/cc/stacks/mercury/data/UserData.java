package cc.stacks.mercury.data;

import cc.stacks.mercury.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@SuppressWarnings("unused")
public interface UserData {

    @Insert("INSERT INTO `user` (`name`,`nickname`,`password`,`admin`,`mfa`,`add_time`) VALUES (#{name},#{nickname},#{password},#{admin},#{mfa},#{addTime})")
    int add(String name,String nickname,String password,boolean admin,String mfa,long addTime);

    @Update("UPDATE `user` SET `name` = #{name},`nickname` = #{nickname},${password}${mfa}`admin` = #{admin} WHERE `id` = #{id}")
    int update(int id,String name,String nickname,String password,String mfa,boolean admin);

    @Update("UPDATE `user` SET `last_login` = #{date} WHERE `id` = #{id}")
    int updateLogin(int id,long date);

    @Delete("DELETE FROM `user` WHERE  `id` = #{id}")
    int delete(int id);

    @Select("SELECT `name`,`nickname`,`admin`,`mfa`,`add_time`,`last_login` FROM `user` WHERE `id` = #{id}")
    User getItem(int id);

    @Select("SELECT `id`,`name`,`nickname`,`admin`,`mfa`,`add_time`,`last_login` FROM `user` WHERE `name` = #{name} AND `password` = #{password}")
    User getPrivacyItem(String name,String password);

    @Select("SELECT `id`,`name`,`nickname`,`admin`,`mfa`,`add_time`,`last_login` FROM `user`")
    List<User> getList();

}
