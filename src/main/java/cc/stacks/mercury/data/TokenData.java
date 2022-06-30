package cc.stacks.mercury.data;

import cc.stacks.mercury.model.Token;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@SuppressWarnings("unused")
public interface TokenData {

    @Insert("INSERT INTO `token` (`code`,`uid`,`external`,`ip`,`platform`,`device`,`issued`,`expire`) VALUES (#{code},#{uid},#{external},#{ip},#{platform},#{device},#{issued},#{expire})")
    int add(String code,int uid,boolean external,String ip,String platform,String device,long issued,long expire);

    @Delete("DELETE FROM `token` WHERE  `code` = #{code}")
    int delete(String code);

    @Delete("DELETE FROM `token` WHERE  `uid` = #{uid}")
    int deleteByUId(int uid);

    @Delete("DELETE FROM `token` WHERE  `external` = #{external}")
    int deleteByExternal(boolean external);

    @Select("SELECT `code`,`uid`,`external`,`ip`,`platform`,`device`,`issued`,`expire` FROM `token` WHERE `code` = #{code}")
    Token getItem(String code);

    @Select("SELECT `code`,`uid`,`external`,`ip`,`platform`,`device`,`issued`,`expire` FROM `token` WHERE `uid` = #{uid}")
    List<Token> getList(int uid);

}
