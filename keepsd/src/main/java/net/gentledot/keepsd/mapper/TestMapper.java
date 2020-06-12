package net.gentledot.keepsd.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {
    @Select("select now()")
    String current();

    @Select("select count(1) from ksd_board")
    Long getCount();

    @Insert("insert into tbl_tmp (tmpstr) values #{str}")
    Integer saveTmp(String str);

    @Select("select tmpstr from tbl_tmp where tmpstr = #{tmpStr}")
    String getTmp(String tmpStr);
}
