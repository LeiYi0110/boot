package com.bjxc.school.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bjxc.Result;
import com.bjxc.exception.BusinessException;
import com.bjxc.school.exception.AuthenticateException;
import com.bjxc.school.service.AuthenticateService;
import com.bjxc.school.utils.CommonUtil;
import com.bjxc.userdetails.TokenUserDetailsService;
import com.bjxc.userdetails.UserDetails;


@RestController
@RequestMapping("/app")
public class AppLoginController {
	private static final Logger logger=LoggerFactory.getLogger(AppLoginController.class);
	private static final String reg = "^[1][\\d]{10}";  //手机验证
	@Resource
	private AuthenticateService authenticateService;
	@Resource
	private TokenUserDetailsService tokenService;
	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Result login(String mobile,String password,String app){
		Result result = new Result();
		try{
			Assert.notNull(password, "密码不能为空");
			Assert.notNull(mobile, "号码不能为空");
			if(CommonUtil.startCheck(reg,mobile)){
				String token = authenticateService.authenticate(mobile, password,app);
				result.success(token);
			}
		}catch(BusinessException ex){
			result.error(ex);
			logger.error("login api  ",ex);
		}
		return result;
	}
	
	/**
	 * 注册
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public Result register(String mobile,String password,String app,String identCode){
		Result result = new Result();
		try {
			Assert.notNull(password, "密码不能为空");
			Assert.notNull(mobile, "号码不能为空");
			Assert.notNull(app, "端口不能为空");
			Assert.notNull(identCode, "验证码不能为空");
			authenticateService.register(authenticateService.checkIdentCode(identCode, app, mobile, password,1));
			String token = authenticateService.authenticate(mobile, password,app);
			result.success(token);
		} catch (BusinessException e) {
			result.error(e);
			logger.error("register api  ",e);
		}
		return result;
	}
	
	/**
	 * 注销
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	public Result logout(String token){
		Result result = new Result();
		try {
			Assert.notNull(token, "token不能为空");
			authenticateService.deleteToken(token);
		} catch(BusinessException ex){
			result.error(ex);
			logger.error("logout api  ",ex);
		}
		return result;
	}
	
	/**
	 * 发送验证码
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/identCode/{app}/{mobile}",method=RequestMethod.GET)
	public Result sentCode(@PathVariable("app") String app,@PathVariable("mobile") String mobile,String use) throws IOException{
		Result result = new Result();
		try {
			Assert.notNull(app,"终端不能为空");
			Assert.notNull(mobile,"手机号不能为空");
			Assert.notNull(use,"用途不能为空");
			if(CommonUtil.startCheck(reg,mobile)){
				String code=authenticateService.sendCode(mobile, use, app);
				result.success(code);
			}
		} catch (BusinessException ex) {
			result.error(ex);
			logger.error("sentCode api  ",ex);
		}
		return result;
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	/*
	@RequestMapping(value="/password",method=RequestMethod.POST)
	public Result changePwd(String token,String newPassword,String oldPassword,String mobile,
			String identCode,String app){
		Result result = new Result();
		try {
			UserDetails user=tokenService.loadUserByToken(token);
			
			if (user == null) {
				result.error("请先登录");
			}
			else
			{
				Assert.notNull(newPassword,"新密码不能为空");
				if(newPassword.trim().length()<6){
					throw new AuthenticateException("新密码长度应该大于6位");
				}
				else if(!StringUtils.isEmpty(token)){
					authenticateService.changePwd(authenticateService.oldPwd(token, oldPassword, newPassword));
					
				}
				else if(!StringUtils.isEmpty(mobile)){
					authenticateService.changePwd(authenticateService.checkIdentCode(identCode, app, mobile, newPassword,2));
					result.success("修改成功");
					
				}else {
					throw new AuthenticateException("token和mobile必须有一个");
				}
			}
			
		} catch (BusinessException ex) {
			result.error(ex);
			logger.error("changePwd api  ",ex);
		}
		return result;
	}
	*/
	
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	public Result changePwd(String token,String newPassword,String oldPassword,String app){
		Result result = new Result();
		try {
			Assert.notNull(newPassword,"新密码不能为空");
			
			if(newPassword.trim().length()<6){
				throw new AuthenticateException("新密码长度应该大于6位");
			}
			UserDetails user=tokenService.loadUserByToken(token);
			
			if (user == null) {
				result.error("请先登录");
			}
			else
			{
				authenticateService.changePwd(authenticateService.oldPwd(token, oldPassword, newPassword));
			}
			
		} catch (BusinessException ex) {
			result.error(ex);
			logger.error("changePwd api  ",ex);
		}
		return result;
	}
	@RequestMapping(value="/setPasswordWhenForgot",method=RequestMethod.POST)
	public Result setPasswordWhenForgot(String newPassword,String mobile, String identCode,String app){
		Result result = new Result();
		Assert.notNull(newPassword,"新密码不能为空");
		if(newPassword.trim().length()<6){
			throw new AuthenticateException("新密码长度应该大于6位");
		}
		
		authenticateService.changePwd(authenticateService.checkIdentCode(identCode, app, mobile, newPassword,2));
		result.success("修改成功");
		
		return result;
	}
	
	/**
	 * 修改用户信息
	 * @return
	 */
	@RequestMapping(value="/user/update",method=RequestMethod.POST)
	public Result changeHead(String token,String headImg,String idcard,Integer sex,String userName){
		Result result = new Result();
		Assert.notNull(token,"token不能为空");
		try {
			UserDetails user=tokenService.loadUserByToken(token);
			authenticateService.changeHead(user.getId(),user.getRole(), headImg,idcard,sex,userName);
		} catch (BusinessException e) {
			result.error(e);
			logger.error("changeHead api  ",e);
		}
		return result;
	}
	
	/**
	 * 用户信息获取
	 * @return
	 */
	@RequestMapping(value="/userInfo",method=RequestMethod.GET)
	public Result getInfo(String token){
		Result result = new Result();
		Assert.notNull(token,"token不能为空");
		try {
			UserDetails user=tokenService.loadUserByToken(token);
			result.success(authenticateService.getUser(user.getId()));
		} catch (BusinessException e) {
			result.error(e);
			logger.error("changeHead api  ",e);
		}
		return result;
	}
	
	/*
	@RequestMapping(value="/bindCard", method=RequestMethod.POST)
	public Result bindCard(String token, String checkCode,String cardNum, String mobile)
	{
		Result result = new Result();
		
		Assert.notNull(token,"token不能为空");
		try {
			UserDetails user=tokenService.loadUserByToken(token);
			if (user == null) {
				result.error("请先登录");
			}
			else
			{
				if (authenticateService.checkBindCardCode(checkCode, mobile))
				{
					logger.info("OK");
				}
				else
				{
					result.error("验证码不正确");
				}
			}
			
			
			//result.success(authenticateService.getUser(user.getId()));
		} catch (BusinessException e) {
			result.error(e);
			logger.error("changeHead api  ",e);
		}
		
		return result;
	}
	*/

}
