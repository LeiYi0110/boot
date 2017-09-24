package com.bjxc.exception;

public class DuplicateException extends BusinessException {

	public DuplicateException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public DuplicateException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4661622931331650847L;

	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return 700;
	}

}
