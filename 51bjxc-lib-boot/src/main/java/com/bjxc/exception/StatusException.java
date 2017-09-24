package com.bjxc.exception;

public class StatusException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5357809002468660325L;

	public StatusException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public StatusException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return 700;
	}

}
