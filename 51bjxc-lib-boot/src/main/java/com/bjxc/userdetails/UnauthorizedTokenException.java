package com.bjxc.userdetails;

import com.bjxc.exception.BusinessException;

/**
 * tokenʧЧ�쳣 
 * @author cras
 *
 */
public class UnauthorizedTokenException extends BusinessException {

	public UnauthorizedTokenException(String msg, Throwable cause)  {
		super(msg,cause);
	}
	
	public UnauthorizedTokenException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5977333208013131624L;

	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return 702;
	}

}
