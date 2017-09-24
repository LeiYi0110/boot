package com.bjxc.school.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bjxc.school.User;



/**
* @author yy
* @date : 2016年8月10日 上午10:40:30
*/

@Mapper
public interface UserMapper {
	/**
	 * 登录
	 */
	User login(Map param);
	
	/**
	 * 查询
	 */
	User getInfo(Map param);
	
	/**
	 * 检测用户是否存在
	 * @return 存在大于1
	 */
	int testUser(Map param);
	
	/**
	 * 修改密码
	 */
	Integer changePassword(Map param);
	
	/**
	 * 修改资料
	 */
	Integer changeHead(Map param);
	
	/**
	 * 修改学生头像
	 */
	Integer changeStuHead(Map param);
	
	/**
	 * 修改教练头像
	 */
	Integer changeCocahHead(Map param);
	
	Integer add(Map param);
}
