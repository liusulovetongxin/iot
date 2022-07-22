package com.qf.iotforbiddenpath.mapper;

import com.qf.iotforbiddenpath.dto.Dc3ForbiddenPathDto;
import com.qf.iotforbiddenpath.pojo.Dc3ForbiddenPath;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotforbiddenpath.mapper
 * @Description:
 * @Date 2022/7/22 10:24
 */

public interface ForbiddenPathMapper {

    @SelectKey(statement = "select replace(UUID(),'-','')",keyProperty = "id",before = true,resultType = String.class)
    @Insert("insert into dc3_forbidden_path(id,url_path,description) values(#{id},#{urlPath},#{description})")
    void addPath(Dc3ForbiddenPath dc3ForbiddenPath);

    @Select("select url_path,description from dc3_forbidden_path where id = #{id} and enable =1")
    Dc3ForbiddenPathDto findById(String id);

    @Select("select url_path,description from dc3_forbidden_path where enable =1")
    List<Dc3ForbiddenPathDto> findList();

    @Delete("update dc3_forbidden_path set enable=0,deleted=1 where id = #{id}")
    void deleteById(String id);
}
