package com.bjxc.school.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bjxc.json.JacksonBinder;
import com.bjxc.school.StaticNature;
import com.bjxc.school.User;
import com.bjxc.school.exception.AuthenticateException;
import com.bjxc.school.mapper.UserMapper;
import com.bjxc.school.service.encode.Md5PasswordEncoder;
import com.bjxc.school.utils.CommonUtil;
import com.bjxc.userdetails.TokenUserDetailsService;
import com.bjxc.userdetails.UserDetails;

import ch.qos.logback.core.joran.conditional.ElseAction;


@Service
public class AuthenticateService {
	private static final Logger logger=LoggerFactory.getLogger(AuthenticateService.class);
	private static JacksonBinder binder = JacksonBinder.buildNormalBinder();
	@Resource
	private UserMapper userDao;
	@Resource 
	private TokenUserDetailsService tokenService;
	@Autowired
	private StringRedisTemplate redisTemplate;

	private static final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
	public String authenticate(String mobile,String password,String app){
		String pwd=md5PasswordEncoder.encodePassword(mobile+password, StaticNature.salt);   //密文
		Map<String, Object> map=new HashMap<String, Object>();
		String token=null;
		map.put("mobile", mobile);
		map.put("pwd", pwd);
		User user=userDao.login(map);
		if(user!=null){
			if(user.getStatus()==1){
				if ((app.equals(StaticNature.stuApp)&&user.getRole()!=1)||
						(app.equals(StaticNature.coachApp)&&user.getRole()!=2)) {
					throw new AuthenticateException("请在指定的教练或学员app端登录");
				}
				token=CommonUtil.createToken(user.getId());  //登录令牌
				String val=getRedis(StaticNature.idtokenRedis,user.getId());
				if(val!=null){
					redisTemplate.opsForHash().delete(StaticNature.userRedis, val);
					redisTemplate.opsForHash().delete(StaticNature.idtokenRedis, user.getId()+"");
				}
				map.clear();
				map.put("id", user.getId());
				map.put("mobile", mobile);
				map.put("role", user.getRole());
				map.put("date", CommonUtil.getSimpleDate(new Date(), "yyyy:MM:dd HH:mm:ss"));
				redisTemplate.opsForHash().put(StaticNature.userRedis, token, binder.toJson(map));
				redisTemplate.opsForHash().put(StaticNature.idtokenRedis, user.getId()+"", token);
				logger.info("login user : " + user.getId());
				return token;
			}else {
				throw new AuthenticateException("账户不可用！");
			}
		}
		throw new AuthenticateException("用户名或密码错误，请重新输入！");
	}

	public void deleteToken(String token){
		redisTemplate.opsForHash().delete(StaticNature.userRedis, token);
		logger.info("logout user : "+token);
	}
	
	public String changePwd(Map map){
		Integer end=userDao.changePassword(map);
		if(end==null||end==0){
			throw new AuthenticateException("旧密码错误！");
		}
		return "success";
	}
	
	public Map<String, Object> oldPwd(String token,String oldPassword,String newPassword){
		Map<String, Object> map=new HashMap<String, Object>();
		UserDetails user=tokenService.loadUserByToken(token);
		Assert.notNull(oldPassword.trim(),"旧密码不能为空");
		String oldpwd=md5PasswordEncoder.encodePassword(user.getMobile()+oldPassword, StaticNature.salt);
		String newpwd=md5PasswordEncoder.encodePassword(user.getMobile()+newPassword, StaticNature.salt);
		map.put("oldpwd", oldpwd);
		map.put("id", user.getId());
		map.put("newpwd", newpwd);
		return map;
	}
	
	public boolean checkBindCardCode(String checkCode, String mobile)
	{
		String testCode ;
		try {
			testCode=getRedis(StaticNature.stuBankCard, "bank_"+mobile+":code");
		} catch (Exception e) {
			// TODO: handle exception
			throw new AuthenticateException("未发送验证码");
		}
		
		if (testCode.equals(checkCode) ) {
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * 检查验证码有效性
	 * @return
	 */
	public Map<String, Object> checkIdentCode(String identCode,String app,String mobile,String newPassword, Integer use){
		Map<String, Object> map=new HashMap<String, Object>();
		Assert.notNull(identCode.trim(),"验证码不能为空");
		Assert.notNull(app.trim(),"端口不能为空");
		
		String testCode=null;
		Long timeMils=null;
		if (use.intValue() == 1) {//注册
			
			if(app.equals(StaticNature.stuApp)){				//判断客户端
				map.put("role", 1);
				testCode=getRedis(StaticNature.stuCode, "r_"+mobile+":code");
				timeMils=Long.parseLong(getRedis(StaticNature.stuCode, "r_"+mobile+":time"));
				
			}else if (app.equals(StaticNature.coachApp)) {
				map.put("role", 2);
				testCode=getRedis(StaticNature.coachCode, "r_"+mobile+":code");
				timeMils=Long.parseLong(getRedis(StaticNature.coachCode, "r_"+mobile+":time"));
			}
			
		}
		else if (use.intValue() == 2) {//忘记密码
			
			if(app.equals(StaticNature.stuApp)){				//判断客户端
				map.put("role", 1);
				testCode=getRedis(StaticNature.stuCode, "f_"+mobile+":code");
				timeMils=Long.parseLong(getRedis(StaticNature.stuCode, "f_"+mobile+":time"));
			}else if (app.equals(StaticNature.coachApp)) {
				map.put("role", 2);
				testCode=getRedis(StaticNature.coachCode, "f_"+mobile+":code");
				timeMils=Long.parseLong(getRedis(StaticNature.coachCode, "f_"+mobile+":time"));
			}
			
		}
		/*
		if(app.equals(StaticNature.stuApp)){				//判断客户端
			map.put("role", 1);
			try {
				testCode=getRedis(StaticNature.stuCode, "f_"+mobile+":code");
				timeMils=Long.parseLong(getRedis(StaticNature.stuCode, "f_"+mobile+":time"));
			} catch (NumberFormatException e) {
				testCode=getRedis(StaticNature.stuCode, "r_"+mobile+":code");
				timeMils=Long.parseLong(getRedis(StaticNature.stuCode, "r_"+mobile+":time"));
			}
		}else if (app.equals(StaticNature.coachApp)) {
			map.put("role", 2);
			try {
				testCode=getRedis(StaticNature.coachCode, "f_"+mobile+":code");
				timeMils=Long.parseLong(getRedis(StaticNature.coachCode, "f_"+mobile+":time"));
			} catch (NumberFormatException e) {
				testCode=getRedis(StaticNature.coachCode, "r_"+mobile+":code");
				timeMils=Long.parseLong(getRedis(StaticNature.coachCode, "r_"+mobile+":time"));
			}
		}
		*/
		
		if (System.currentTimeMillis()-timeMils>600000) {
			throw new AuthenticateException("验证码超时！");
		}else if (!identCode.trim().equals(testCode)) {
			throw new AuthenticateException("验证码无效！");
		}

		String newpwd = md5PasswordEncoder.encodePassword(mobile+newPassword, StaticNature.salt);
		map.put("newpwd", newpwd);
		map.put("mobile", mobile);
		return map;
	}
	
	/**
	 * 发送验证码（手机，redis）
	 * @throws IOException
	 */
	public String sendCode(String mobile,String use,String app) throws IOException{
		Map<String, String> map=new HashMap<String, String>();
		map.put("mobile", mobile);
		map.put("status", null);
		String testCode=CommonUtil.getRandomString();
		if(use.equals("2")){					//忘记密码
			map.put("status", "1");
			if(userDao.testUser(map)!=0){		//用户是否存在
				sendCheck(mobile, "f_", app, testCode);
				CommonUtil.sendTemplateSMS(mobile, testCode);
				return testCode;
			}else {
				throw new AuthenticateException("用户不存在或不可用");
			}
		}else if (use.equals("1")) {//login
			if(userDao.testUser(map)>0){
				throw new AuthenticateException("用户已存在");
			}else {
				sendCheck(mobile, "r_", app, testCode);
				CommonUtil.sendTemplateSMS(mobile, testCode);
				return testCode;
			}
		}
		else if (use.equals("3")) {//绑定银行卡
			
			sendCheck(mobile, "bank_", app, testCode);
			CommonUtil.sendTemplateSMS(mobile, testCode);
			return testCode;
			
		}
		throw new AuthenticateException("操作有误");
	}

	public void sendCheck(String mobile,String use,String app,String testCode){
		String role=null;                       //身份
		if(app.equals(StaticNature.stuApp)){
			role=StaticNature.stuCode;
		}else if(app.equals(StaticNature.coachApp)) {
			role=StaticNature.coachCode;
		}else {
			throw new AuthenticateException("端口错误未找到");
		}
		
		if (use.equals("bank_")) {
			role=StaticNature.stuBankCard;
		}
		StringBuffer key=new StringBuffer();                 //reids key
		StringBuffer once=new StringBuffer();                //短信发送次数key（注册和忘记密码分开）
		StringBuffer time=new StringBuffer();                //发送时间 
		Integer times=null;                    //短信发送次数
		key.append(use+mobile);		
		once.append(key+":once");
		time.append(key+":time");
		String lastSendTime=getRedis(role, time.toString());
		if(lastSendTime!=null){			
			Long ionceTime=Long.parseLong(getRedis(role, key+":onceTime"));
			Long nowTime=System.currentTimeMillis();
			if(nowTime-ionceTime>86400000){		//24小时内第一次发送至最近一次发送
				times=0;
				redisTemplate.opsForHash().put(role, key+":onceTime", System.currentTimeMillis()+"");
			}else {
				times=1;
				//times=Integer.parseInt(getRedis(role, once.toString()));
			}
		}
		/*
		if(times!=null){
			if(times<11){
				times++;
			}else {
				throw new AuthenticateException("验证码发送达到24小时内上限");
			}
		}else {
			times=1;
			redisTemplate.opsForHash().put(role, key+":onceTime", System.currentTimeMillis()+"");
		}
		*/
		redisTemplate.opsForHash().put(role, key+":onceTime", System.currentTimeMillis()+"");
		redisTemplate.opsForHash().put(role, key+":code", testCode);
		redisTemplate.opsForHash().put(role, time.toString(), System.currentTimeMillis()+"");
		redisTemplate.opsForHash().put(role, once.toString(), times.toString());
	}
	
	public void changeHead(Integer id,Integer role,String headImg,String idcard,Integer sex,String userName){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("src", headImg);
		map.put("idcard", idcard);
		map.put("sex", sex);
		map.put("userName", userName);
		userDao.changeHead(map);
		
		if (!StringUtils.isEmpty(idcard) || !StringUtils.isEmpty(userName) || sex != null) {
			if(role==1){
				userDao.changeStuHead(map);
			}else {
				userDao.changeCocahHead(map);
			}
		}
		
	}
	
	public Map getUser(Integer id){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		User userinfo=userDao.getInfo(map);
//		map.clear();
		map.put("Mobile", userinfo.getMobile());
		map.put("Headimg", userinfo.getHeadimg());
		map.put("Idcard", userinfo.getIdCard());
		map.put("Sex", userinfo.getSex());
		map.put("UserName", userinfo.getUserName());
		map.put("Mobile", userinfo.getMobile());
		return map;
	}
	
	public Boolean checkUserPwd(Integer id, String pwd){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		User userinfo=userDao.getInfo(map);
		
		if (userinfo.getPassword().equals(md5PasswordEncoder.encodePassword(userinfo.getMobile()+pwd, StaticNature.salt))) {
			
			return true;
			
		}
		return false;
	}
	
	/**
	 * 注册
	 */
	public void register(Map map){
		userDao.add(map);
	}
	
	/**
	 * redis获取
	 * @return
	 */
    public String getRedis(String field,Object key) {
    	Object result=redisTemplate.opsForHash().get(field, key+"");
    	if(result==null){
    		return null;
    	}
    	return (String)result;
	}
}
