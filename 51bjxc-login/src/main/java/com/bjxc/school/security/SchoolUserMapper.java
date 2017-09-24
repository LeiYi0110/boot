package com.bjxc.school.security;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
public interface SchoolUserMapper {
	 //模拟从数据库查询用户，查询到用户名相同，就返回该用户对象。(如果配置了DB的话，就可以使用sql查询。这里使用list数据模拟)
	@Results(value ={
            @Result(property="role",column="Urole",javaType=Integer.class,jdbcType=JdbcType.INTEGER),
            })
	@Select("select pu.*,it.name insName,it.inscode as insCode from TPlatformUser pu left join TInstitution it on pu.insId = it.id    where pu.UserName = #{userName}")
	 public SchoolUser loadSchoolUserByUserName(@Param("userName")String userName);
}
