package cc.stacks.mercury.data;

import cc.stacks.mercury.model.Mark;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@SuppressWarnings("unused")
public interface MarkData {

    @Insert("INSERT INTO `mark` (`gid`, `uid`, `ssid`, `title`, `icon`, `describe`, `weight`, `service`, `era`, `ira`, `hide`, `date`) VALUES (#{gid},#{uid},#{ssid},#{title},#{icon},#{describe},#{weight},#{service},#{era},#{ira},#{hide},#{date})")
    int add(int gid,int uid,int ssid,String title,String icon,String describe,int weight,boolean service,String era,String ira,int hide,long date);

    @Update("UPDATE `mark` SET `gid` = #{gid},`title` = #{title},`describe` = #{describe},`weight` = #{weight},`era` = #{era},`ira` = #{ira},`hide` = #{hide},`date` = #{date} WHERE `id` = #{id}")
    int update(int id,int gid,String title,String describe,int weight,String era,String ira,int hide,long date);

    @Delete("DELETE FROM `mark` WHERE  `id` = #{id}")
    int delete(int id);

    @Select("SELECT `id`,`gid`,`uid`,`ssid`,`title`,`icon`,`describe`,`weight`,`service`,`era`,`ira`,`hide`,`date` FROM `mark` WHERE `id` = #{id}")
    Mark getItem(int id);

    @Select("SELECT `id`,`gid`,`uid`,`ssid`,`title`,`icon`,`describe`,`weight`,`service`,`era`,`ira`,`hide`,`date` FROM `mark` ${screen} ORDER BY `weight` DESC")
    List<Mark> getList(String screen);

}