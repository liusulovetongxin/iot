package com.qf.iotblackip.mapper;

import com.qf.iotblackip.dto.BlackIpDto;
import com.qf.iotblackip.pojo.Dc3BlackIp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackip.mapper
 * @Description:
 * @Date 2022/7/20 16:32
 */

public interface BlackIpMapper {

    @SelectKey(statement = "select replace(uuid(),'-','')",keyProperty = "id",before = true,resultType = String.class)
    @Insert("insert into dc3_black_ip(id,ip,description) values(#{id},#{ip},#{description})")
    void addBlackIp(Dc3BlackIp dc3BlackIp);
    @Select("select ip,description from dc3_black_ip where enable = 1")
    List<BlackIpDto> findAllIp2Dto();

    @Delete("update dc3_black_ip set enable=0,deleted=1 where id = #{id}")
    Long deleteId(String id);
}
