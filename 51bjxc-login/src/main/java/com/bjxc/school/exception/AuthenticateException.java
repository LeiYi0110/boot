package com.bjxc.school.exception;

import com.bjxc.exception.BusinessException;

/**
 * 认证异常
 * @author Administrator
 *
 */
public class AuthenticateException extends BusinessException {
	

	public AuthenticateException(String msg, Throwable cause)  {
		super(msg,cause);
	}
	
	public AuthenticateException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -636514916136723613L;
	
	@Override
	public Integer getCode() {
		return 701;
	}
}
