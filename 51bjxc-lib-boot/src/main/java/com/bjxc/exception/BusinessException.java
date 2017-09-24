package com.bjxc.exception;

import org.springframework.core.NestedRuntimeException;

public abstract class BusinessException extends NestedRuntimeException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1055292751059342167L;

	public BusinessException(String msg, Throwable cause)  {
		super(msg,cause);
	}
	
	public BusinessException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	public abstract Integer getCode();
}
